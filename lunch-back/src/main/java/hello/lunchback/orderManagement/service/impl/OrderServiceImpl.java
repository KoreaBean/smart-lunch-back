package hello.lunchback.orderManagement.service.impl;

import hello.lunchback.common.response.ResponseDto;
import hello.lunchback.login.entity.MemberEntity;
import hello.lunchback.login.repository.MemberRepository;
import hello.lunchback.orderManagement.dto.request.PostOrderRequestDto;
import hello.lunchback.orderManagement.dto.response.GetOrderHistoryResponseDto;
import hello.lunchback.orderManagement.dto.response.OrderHistoryItem;
import hello.lunchback.orderManagement.dto.response.PostOrderResponseDto;
import hello.lunchback.orderManagement.entity.OrderDetailEntity;
import hello.lunchback.orderManagement.entity.OrderEntity;
import hello.lunchback.orderManagement.service.OrderService;
import hello.lunchback.storeManagement.entity.StoreEntity;
import hello.lunchback.storeManagement.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

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
    public PostOrderResponseDto order(Integer storeId, String email, PostOrderRequestDto dto) {
        MemberEntity member = new MemberEntity();
        StoreEntity store = new StoreEntity();
        try {
            // 멤버 조회 하고
            member = memberRepository.findByMemberEmail(email)
                    .orElse(null);
            if (member == null){
                PostOrderResponseDto.notExistedUser();
            }
            store = storeRepository.findByStoreId(storeId)
                    .orElse(null);
            member.getOrderList().add(new OrderEntity(store,dto,member));
            // new order 만들고
            // orderDetail 만들고
            // 카카오페이 결제 연결 하고
            // ok 떨어지면 결제 승인 -> 완료
            // -> 가게 알람
            // -> 사용자 알람
        }catch (Exception e){
            e.printStackTrace();
            return PostOrderResponseDto.databaseErr();
        }
        return PostOrderResponseDto.success();
    }

    private OrderHistoryItem mappingData(OrderEntity order) {
        OrderHistoryItem item = new OrderHistoryItem();
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
