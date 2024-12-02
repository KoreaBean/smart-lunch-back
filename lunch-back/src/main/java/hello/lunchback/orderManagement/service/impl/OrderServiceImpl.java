package hello.lunchback.orderManagement.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.lunchback.common.response.ResponseDto;
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
import hello.lunchback.orderManagement.repository.OrderRepository;
import hello.lunchback.orderManagement.service.OrderService;
import hello.lunchback.storeManagement.entity.StoreEntity;
import hello.lunchback.storeManagement.repository.StoreRepository;
import hello.lunchback.waitManagement.WaitingManager;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final SimpMessagingTemplate messagingTemplate;

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
            // 4. 결제 완료 여부
            orderEntity.setPay(true);
            // 5. 상점 대기열에 고객 추가
            waitingManager.add(storeId,orderEntity.getOrderId());
            // 6. 알람 전송
            sendToStoreAlam(dto, orderEntity, result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return kakaopayResponseDto;
    }

    private void sendToStoreAlam(PostOrderRequestDto dto, OrderEntity orderEntity, memberAuthentication result) throws JsonProcessingException {
        OrderNotificationDto notificationDto = new OrderNotificationDto(orderEntity.getOrderId(), orderEntity.getOrderDate(), dto.getTotalPrice());
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage = objectMapper.writeValueAsString(notificationDto);
        //messagingTemplate.convertAndSend("/room/"+ result.store().getMember().getMemberId(),jsonMessage);
        messagingTemplate.convertAndSend("/room/"+1,jsonMessage);
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
                .approval_url("http://localhost:8080/kakaopay/payment/success")
                .fail_url("http://localhost:8080/kakaopay/payment/fail")
                .cancel_url("http://localhost:8080/kakaopay/payment/cancel")
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
