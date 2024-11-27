package hello.lunchback.storeManagement.service;

import hello.lunchback.menuManagement.dto.response.GetStoreMenuListResponseDto;
import hello.lunchback.storeManagement.dto.response.GetStoreListResponseDto;
import hello.lunchback.storeManagement.dto.response.GetStoreResponseDto;

public interface StoreService {
    GetStoreResponseDto storeList(String email);


    GetStoreListResponseDto storeMenuList(Integer storeId);
}
