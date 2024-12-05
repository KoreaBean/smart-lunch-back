package hello.lunchback.storeManagement.service;

import hello.lunchback.menuManagement.dto.response.GetStoreMenuListResponseDto;
import hello.lunchback.storeManagement.dto.request.PostStoreCreateRequestDto;
import hello.lunchback.storeManagement.dto.response.*;
import org.springframework.http.ResponseEntity;

public interface StoreService {
    GetStoreResponseDto storeList(String email);


    GetStoreListResponseDto storeMenuList(Integer storeId);

    ResponseEntity<? super GetStoreOrderResponseDto> storeOrderList(String email);

    ResponseEntity<? super GetStoreOrderDetailResponseDto> storeOrderDetail(String email, Integer orderId);

    ResponseEntity<? super DeleteStoreOrderResponseDto> orderDelete(String email, Integer orderId);

    ResponseEntity<? super PostStoreCreateResponseDto> storeCreate(String email, PostStoreCreateRequestDto dto);
}
