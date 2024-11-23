package hello.lunchback.orderManagement.service.impl;

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
    public GetOrderHistoryResponseDto orderHistory(String email) {
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
            // 멤버 조회 하고
            member = memberRepository.findByMemberEmail(email)
                    .orElse(null);
            if (member == null){
                PostOrderResponseDto.notExistedUser();
            }
            store = storeRepository.findByStoreId(storeId)
                    .orElse(null);
            OrderEntity orderEntity = new OrderEntity(store, dto, member);
            orderRepository.save(orderEntity);
            member.getOrderList().add(orderEntity);
            // new order 만들고
            // orderDetail 만들고
            // 카카오페이 결제 연결 하고
            // ok 떨어지면 결제 승인 -> 완료
            // -> 가게 알람
            // -> 사용자 알람
            StringBuilder sb = new StringBuilder();
            Integer quantity = 0;
            for (MenuListItem menuListItem : dto.getList()) {
                    sb.append(menuListItem.getMenuName());
                    quantity = quantity + menuListItem.getQuantity();
            }


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
            kakaopayResponseDto = kakaoService.requestPayment(kakaopayRequestDto);
            orderEntity.setPay(true);
            orderRepository.save(orderEntity);
            waitingManager.add(storeId,orderEntity.getOrderId());
            String message = "새로운 주문이 도착했습니다.";
            messagingTemplate.convertAndSend("/room/"+ store.getMember().getMemberId(),message);
        }catch (Exception e){
            e.printStackTrace();
        }
        return kakaopayResponseDto;
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
            GetOrderHistoryDetailItem result = new GetOrderHistoryDetailItem(orderDetailEntity.getMenuName(), orderDetailEntity.getMenuPrice(), orderDetailEntity.getQuantity());
            totalPrice =+ result.getTotalPrice();
            list.add(result);
        }


        return GetOrderHistoryDetailResponseDto.success(order,list,totalPrice,busy,myWait,predict);
    }

    private String predictTime(Integer myWait) {
        if (myWait <= 3){
            return "30";
        }
        if (myWait > 3 && myWait <= 10){
            return "60";
        }
        return "60분 이상";
    }

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

    private OrderEntity getOrder(Integer orderId, MemberEntity member) {
        return member.getOrderList().stream()
                .filter(order -> orderId.equals(orderId))
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
