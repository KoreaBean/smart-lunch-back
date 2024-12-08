package hello.lunchback.orderManagement.service.impl;

import hello.lunchback.external.kakaoPay.KakaoService;
import hello.lunchback.external.kakaoPay.dto.request.KakaopayRequestDto;
import hello.lunchback.external.kakaoPay.dto.response.KakaopayResponseDto;
import hello.lunchback.login.entity.MemberEntity;
import hello.lunchback.login.repository.MemberRepository;
import hello.lunchback.orderManagement.dto.request.MenuListItem;
import hello.lunchback.orderManagement.dto.request.PostOrderRequestDto;
import hello.lunchback.orderManagement.dto.response.*;
import hello.lunchback.orderManagement.entity.OrderDetailEntity;
import hello.lunchback.orderManagement.entity.OrderEntity;
import hello.lunchback.orderManagement.repository.OrderDetailRepository;
import hello.lunchback.orderManagement.repository.OrderRepository;
import hello.lunchback.orderManagement.service.OrderService;
import hello.lunchback.storeManagement.entity.StoreEntity;
import hello.lunchback.storeManagement.repository.StoreRepository;
import hello.lunchback.waitManagement.WaitingManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final WaitingManager waitingManager;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final KakaoService kakaoService;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Value("${file.fileUrl}")
    private String fileUrl;


    // 주문 내역 조회
    @Override
    public GetOrderHistoryResponseDto getOrderHistoryList(String email) {
        List<OrderEntity> orderList = new ArrayList<>();
        List<OrderHistoryItem> historyItems = new ArrayList<>();
        try {
            MemberEntity member = memberRepository.findByMemberEmail(email)
                    .orElse(null);
            if (member == null){
                return GetOrderHistoryResponseDto.notExistedUser();
            }
            orderList = member.getOrderList();
            for (OrderEntity orderEntity : orderList) {
                historyItems.add(mappingData(orderEntity));
            }
        }catch (Exception e){
            e.printStackTrace();
            return GetOrderHistoryResponseDto.databaseErr();
        }
        return GetOrderHistoryResponseDto.success(historyItems);
    }

    // 주문하기
    @Override
    @Transactional
    public KakaopayResponseDto order(Integer storeId, String email, PostOrderRequestDto dto) {
        MemberEntity member = new MemberEntity();
        StoreEntity store = new StoreEntity();
        KakaopayResponseDto kakaopayResponseDto = new KakaopayResponseDto();
        try {
            // 1. 멤버 검증
            memberAuthentication result = getMemberAuthentication(storeId, email);
            // 2. orderEntity, orderDetailEntity 생성
            OrderEntity orderEntity = saveOrderInfo(dto, result.store(), result.member());
            // 3. 결제 요청
            kakaopayResponseDto = getKakaopayResponseDto(dto, orderEntity, result, kakaopayResponseDto);

        }catch (Exception e){
            e.printStackTrace();
        }
        return kakaopayResponseDto;
    }

    private KakaopayResponseDto getKakaopayResponseDto(PostOrderRequestDto dto, OrderEntity orderEntity, memberAuthentication result, KakaopayResponseDto kakaopayResponseDto) {
        StringBuilder sb = new StringBuilder();
        Integer quantity = 0;
        for (MenuListItem menuListItem : dto.getList()) {
                sb.append(menuListItem.getMenuName());
                quantity = quantity + menuListItem.getQuantity();
        }
        KakaopayRequestDto kakaopayRequestDto = getKakaopayRequestDto(dto, orderEntity, result.member(), sb, quantity);

        kakaopayResponseDto = kakaoService.requestPayment(kakaopayRequestDto);
        return kakaopayResponseDto;
    }

    private memberAuthentication getMemberAuthentication(Integer storeId, String email) {
        StoreEntity store;
        MemberEntity member;
        member = memberRepository.findByMemberEmail(email)
                .orElse(null);
        if (member == null){
            PostOrderResponseDto.notExistedUser();
        }
        store = storeRepository.findByStoreId(storeId)
                .orElse(null);
        memberAuthentication result = new memberAuthentication(member, store);
        return result;
    }

    private record memberAuthentication(MemberEntity member, StoreEntity store) {
    }

    // 결제 요청
    private static KakaopayRequestDto getKakaopayRequestDto(PostOrderRequestDto dto, OrderEntity orderEntity, MemberEntity member, StringBuilder sb, Integer quantity) {
        KakaopayRequestDto kakaopayRequestDto = KakaopayRequestDto.builder()
                .cid("TC0ONETIME")
                .partner_order_id(String.valueOf(orderEntity.getOrderId()))
                .partner_user_id(String.valueOf(member.getMemberId()))
                .item_name(sb.toString())
                .quantity(quantity)
                .total_amount(dto.getTotalPrice())
                .tax_free_amount(0)
                .approval_url("http://3.35.209.148:8080/kakaopay/payment/success")
                .fail_url("http://3.35.209.148:8080/kakaopay/payment/fail")
                .cancel_url("http://3.35.209.148:8080/kakaopay/payment/cancel")
                .build();
        return kakaopayRequestDto;
    }

    private OrderEntity saveOrderInfo(PostOrderRequestDto dto, StoreEntity store, MemberEntity member) {
        OrderEntity orderEntity = new OrderEntity(store, dto, member);
        orderRepository.save(orderEntity);
        member.getOrderList().add(orderEntity);
        return orderEntity;
    }

    // 주문 상세 정보 조회
    @Override
    public GetOrderHistoryDetailResponseDto orderHistoryDetail(Integer orderId, String email) {

        MemberEntity member = memberRepository.findByMemberEmail(email)
                .orElse(null);

        OrderEntity order = getOrder(orderId, member);
        Integer myWait = waitingManager.findUser(order.getStore().getStoreId(), orderId);
        String busy = isBusy(order);
        String predict = predictTime(myWait);

        List<GetOrderHistoryDetailItem> list = new ArrayList<>();
        Integer totalPrice = 0;
        for (OrderDetailEntity orderDetailEntity : order.getOrderDetail()) {
            GetOrderHistoryDetailItem result = new GetOrderHistoryDetailItem(orderDetailEntity.getMenuName(),
                    orderDetailEntity.getMenuPrice(), orderDetailEntity.getQuantity());
            totalPrice =+ result.getTotalPrice();
            list.add(result);
        }


        return GetOrderHistoryDetailResponseDto.success(order,list,totalPrice,busy,myWait,predict);
    }

    // 사용자 주문 내역 조회 v2
    @Override
    @Transactional
    public ResponseEntity<? super GetOrderHistoryResponseDtoV2> getOrderHistoryV2(String email) {
        List<Order> orderList = new ArrayList<>();
        try {

            MemberEntity member = memberRepository.findByMemberEmail(email)
                    .orElse(null);
            mappingData(member, orderList);
        }catch (Exception e){
            e.printStackTrace();
            return GetOrderHistoryResponseDtoV2.databaseError();
        }

        return GetOrderHistoryResponseDtoV2.success(orderList);
    }

    private void mappingData(MemberEntity member, List<Order> orderList) {
        for (OrderEntity orderEntity : member.getOrderList()) {
            String storeImg = fileUrl + orderEntity.getStore().getStoreImage();
            // 내 순서
            Integer myWait = waitingManager.findUser(orderEntity.getStore().getStoreId(), orderEntity.getOrderId());
            // 예상 시간
            String waitingTime = predictTime(myWait);
            // 혼잡도
            String busy = isBusy(orderEntity);
            List<OrderItemV2> items = new ArrayList<>();
            Long totalPrice = 0L;
            for (OrderDetailEntity orderDetailEntity : orderDetailRepository.findByOrderOrderId(orderEntity.getOrderId())) {
                OrderItemV2 item = new OrderItemV2(orderDetailEntity);
                items.add(item);
                int price = orderDetailEntity.getMenuPrice() * orderDetailEntity.getQuantity();
                totalPrice = totalPrice + price;
            }
            Order order = new Order(orderEntity, storeImg,myWait,waitingTime,busy, totalPrice,items);
            orderList.add(order);
        }
    }

    // 대기 계산
    private String predictTime(Integer myWait) {
        if (myWait <= 3){
            return "30";
        }
        if (myWait > 3 && myWait <= 10){
            return "60";
        }
        return "60분 이상";
    }
    // 혼잡도 계산
    private String isBusy(OrderEntity order) {
        Integer busy = waitingManager.busy(order.getStore().getStoreId());
        if (busy <= 5){
            return "쾌적";
        }
        if (busy >5 && busy <= 10){
            return "보통";
        }
        return "혼잡";
    }

    private OrderEntity getOrder(Integer orderID, MemberEntity member) {
        return member.getOrderList().stream()
                .filter(order -> order.getOrderId().equals(orderID))
                .findFirst()
                .orElse(null);
    }

    private OrderHistoryItem mappingData(OrderEntity order) {
        OrderHistoryItem item = new OrderHistoryItem();
        item.setOrderId(order.getOrderId());
        item.setOrderDate(order.getOrderDate());
        item.setStoreImage(order.getStore().getStoreImage());
        item.setStoreName(order.getStore().getStoreName());
        for (OrderDetailEntity orderDetailEntity : order.getOrderDetail()) {
            item.getMenuName().add(orderDetailEntity.getMenuName());
            item.setTotalPrice(item.getTotalPrice() + orderDetailEntity.getMenuPrice());
        }
        return item;
    }

}
