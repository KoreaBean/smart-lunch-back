package hello.lunchback.storeManagement.controller;

import hello.lunchback.storeManagement.dto.response.GetStoreListResponseDto;
import hello.lunchback.storeManagement.dto.response.GetStoreResponseDto;
import hello.lunchback.storeManagement.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    // 가게 목록 조회
    @GetMapping(value = "/store", produces = MediaType.APPLICATION_JSON_VALUE)
    public GetStoreResponseDto storeList(@AuthenticationPrincipal String email){
        GetStoreResponseDto result = storeService.storeList(email);
        return result;
    }


    // 가게 메뉴 조회
    @GetMapping(value = "/store/{storeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GetStoreListResponseDto getStoreMenuList(@PathVariable(name = "storeId") Integer storeId){
        GetStoreListResponseDto result = storeService.storeMenuList(storeId);
        return result;
    }


}
