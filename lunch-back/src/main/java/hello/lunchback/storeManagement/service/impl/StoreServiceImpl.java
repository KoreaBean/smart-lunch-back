package hello.lunchback.storeManagement.service.impl;

import hello.lunchback.login.entity.MemberEntity;
import hello.lunchback.login.repository.MemberRepository;
import hello.lunchback.menuManagement.entity.MenuEntity;
import hello.lunchback.orderManagement.dto.OrderStatus;
import hello.lunchback.orderManagement.entity.OrderDetailEntity;
import hello.lunchback.orderManagement.entity.OrderEntity;
import hello.lunchback.storeManagement.dto.response.*;
import hello.lunchback.storeManagement.entity.StoreEntity;
import hello.lunchback.storeManagement.repository.StoreRepository;
import hello.lunchback.storeManagement.service.StoreService;
import hello.lunchback.waitManagement.WaitingManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final WaitingManager waitingManager;

    @Value("${file.fileUrl}")
    private String fileUrl;
    @Override
    public GetStoreResponseDto storeList(String email) {
        List<StoreEntity> all = storeRepository.findAll();
        List<StoreItem> list = new ArrayList<>();
        String state = "";
        for (StoreEntity storeEntity : all) {
            Integer busy = waitingManager.busy(storeEntity.getStoreId());
            if (busy < 3){
                state = "쾌적";
            } else if (busy < 10){
                state = "보통";
            } else {
                state = "매우 혼잡";
            }
            StoreItem storeItem = new StoreItem(storeEntity,state);
            list.add(storeItem);
        }
        return GetStoreResponseDto.success(list);
    }

    @Override
    public GetStoreListResponseDto storeMenuList(Integer storeId) {
        StoreEntity storeEntity = storeRepository.findByStoreId(storeId)
                .orElse(null);
        List<MenuInfoItem> list = new ArrayList<>();
        for (MenuEntity menuEntity : storeEntity.getMenuList()) {
            String menuImg = fileUrl + menuEntity.getMenuImage();
            MenuInfoItem menuInfoItem = new MenuInfoItem(menuEntity,menuImg);

            list.add(menuInfoItem);
        }

        return GetStoreListResponseDto.success(list);
    }

    @Override
    public ResponseEntity<? super GetStoreOrderResponseDto> storeOrderList(String email) {
        // 해당 가게의 주문 이력들 조회해서 반환
        List<OrderEntity> storeOrderList = new ArrayList<>();
        List<OrderItem> orderItemList = new ArrayList<>();
        try {

            // 1. 유저 검증
            MemberEntity member = isCheckMember(email);
            if (member == null){
                return GetStoreOrderResponseDto.notExistedUser();
            }
            // 2. 식당 검증
            Boolean checkStore = isCheckStore(member);
            if (!checkStore){
                return GetStoreOrderResponseDto.notExistedStore();
            }

            // 3. 식당 오더 리스트
            storeOrderList = getStoreOrderList(member);
            if (storeOrderList == null){
                return GetStoreOrderResponseDto.notExistedOrderList();
            }
            totalPrice(storeOrderList, orderItemList);
        }catch (Exception e){
            e.printStackTrace();
            GetStoreOrderResponseDto.databaseError();
        }
        // 4. 리턴
        //  orderId , 주문 일자, 결제 금액
        return GetStoreOrderResponseDto.success(orderItemList);
    }

    @Override
    public ResponseEntity<? super GetStoreOrderDetailResponseDto> storeOrderDetail(String email, Integer orderId) {

        StoreOrderDetailDto dto = new StoreOrderDetailDto();
        try {
            // 1. 유저 검증
            MemberEntity member = isCheckMember(email);
            if (member == null){
                return GetStoreOrderDetailResponseDto.notExistedUser();
            }
            // 2. 식당 검증
            Boolean checkStore = isCheckStore(member);
            if (!checkStore){
                return GetStoreOrderDetailResponseDto.notExistedStore();
            }

            OrderEntity orderEntity = member.getStore().getOrder().stream()
                    .filter(order -> order.getOrderId().equals(orderId))
                    .findFirst()
                    .orElse(null);

            MemberEntity consumer = orderEntity.getMember();
            List<OrderDetailEntity> menuList = orderEntity.getOrderDetail();
            dto.setData(menuList,orderEntity,consumer);
        }catch (Exception e){
            e.printStackTrace();
            return GetStoreOrderDetailResponseDto.databaseError();
        }
        return GetStoreOrderDetailResponseDto.success(dto);
    }

    @Override
    @Transactional
    public ResponseEntity<? super DeleteStoreOrderResponseDto> orderDelete(String email, Integer orderId) {

        try {
            MemberEntity member = memberRepository.findByMemberEmail(email)
                    .orElse(null);
            StoreEntity store = member.getStore();
            OrderEntity orderEntity = store.getOrder().stream()
                    .filter(order -> order.getOrderId().equals(orderId))
                    .findFirst()
                    .orElse(null);
            orderEntity.setStatus(OrderStatus.cancel);

        }catch (Exception e){
            e.printStackTrace();
            return DeleteStoreOrderResponseDto.databaseError();
        }
        return DeleteStoreOrderResponseDto.success();
    }

    private void totalPrice(List<OrderEntity> storeOrderList, List<OrderItem> orderItemList) {

        for (OrderEntity orderEntity : storeOrderList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderEntity.getOrderId());
            orderItem.setOrderDate(orderEntity.getOrderDate());
            orderItem.setStatus(orderEntity.getStatus());
            for (OrderDetailEntity orderDetailEntity : orderEntity.getOrderDetail()) {
                Integer totalPrice = 0;
                Integer price = 0;
                Integer quantity = orderDetailEntity.getQuantity();
                Integer menuPrice = orderDetailEntity.getMenuPrice();
                price = quantity * menuPrice;
                totalPrice = totalPrice + price;
                orderItem.setTotalPrice(totalPrice);
            }
            orderItemList.add(orderItem);
        }
    }

    private List<OrderEntity> getStoreOrderList(MemberEntity member) {
        return member.getStore().getOrder();

    }

    private Boolean isCheckStore(MemberEntity member) {
        StoreEntity store = member.getStore();
        if (store == null){
            return false;
        }
        return true;
    }

    private MemberEntity isCheckMember(String email) {
        return memberRepository.findByMemberEmail(email)
                .orElse(null);
    }

}
