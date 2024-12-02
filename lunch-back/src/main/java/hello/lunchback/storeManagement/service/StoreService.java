package hello.lunchback.storeManagement.service;

import hello.lunchback.menuManagement.dto.response.GetStoreMenuListResponseDto;
import hello.lunchback.storeManagement.dto.response.GetStoreListResponseDto;
import hello.lunchback.storeManagement.dto.response.GetStoreOrderDetailResponseDto;
import hello.lunchback.storeManagement.dto.response.GetStoreOrderResponseDto;
import hello.lunchback.storeManagement.dto.response.GetStoreResponseDto;
import org.springframework.http.ResponseEntity;

public interface StoreService {
    GetStoreResponseDto storeList(String email);


    GetStoreListResponseDto storeMenuList(Integer storeId);

    ResponseEntity<? super GetStoreOrderResponseDto> storeOrderList(String email);

    ResponseEntity<? super GetStoreOrderDetailResponseDto> storeOrderDetail(String email, Integer orderId);
}
