package hello.lunchback.storeManagement.service.impl;

import hello.lunchback.login.entity.MemberEntity;
import hello.lunchback.login.entity.RoleEntity;
import hello.lunchback.login.entity.RoleType;
import hello.lunchback.login.repository.MemberRepository;
import hello.lunchback.login.repository.RoleRepository;
import hello.lunchback.menuManagement.entity.FileEntity;
import hello.lunchback.menuManagement.entity.MenuEntity;
import hello.lunchback.menuManagement.repository.FileRepository;
import hello.lunchback.orderManagement.dto.OrderStatus;
import hello.lunchback.orderManagement.entity.OrderDetailEntity;
import hello.lunchback.orderManagement.entity.OrderEntity;
import hello.lunchback.storeManagement.dto.request.PostStoreCreateRequestDto;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final WaitingManager waitingManager;
    private final FileRepository fileRepository;
    private final RoleRepository roleRepository;

    @Value("${file.fileUrl}")
    private String fileUrl;
    @Value("${file.filePath}")
    private String filePath;


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
            String storeImg = fileUrl + storeEntity.getStoreImage();
            StoreItem storeItem = new StoreItem(storeEntity,state,storeImg);
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

    @Override
    @Transactional
    public ResponseEntity<? super PostStoreCreateResponseDto> storeCreate(String email, PostStoreCreateRequestDto dto) {

        try {
            MemberEntity member = memberRepository.findByMemberEmail(email)
                    .orElse(null);
            if (member == null){
                return PostStoreCreateResponseDto.notExistedUser();
            }
            String uuidfilename = transImageName(dto);
            RoleEntity byRoleName = roleRepository.findByRoleName(RoleType.restaurant.toString());
            member.addRole(byRoleName);
            member.setStore(dto,uuidfilename);

        }catch (Exception e){
            e.printStackTrace();
            return PostStoreCreateResponseDto.databaseError();
        }
        return PostStoreCreateResponseDto.success();
    }

    private String transImageName(PostStoreCreateRequestDto dto) {


            // 1. 확장자 추출
            MultipartFile storeImg = dto.getStoreImg();
            String originalFilename = storeImg.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uuidFilename = UUID.randomUUID() + extension;
            String saveFile = filePath + uuidFilename;
            try {
                storeImg.transferTo(new File(saveFile));
            }catch (Exception e){
                e.printStackTrace();
            }
        fileRepository.save(new FileEntity(uuidFilename,originalFilename));
        return uuidFilename;

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
