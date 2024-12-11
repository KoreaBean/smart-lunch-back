package hello.lunchback.storeManagement.controller;

import hello.lunchback.storeManagement.dto.request.PostStoreCreateRequestDto;
import hello.lunchback.storeManagement.dto.response.*;
import hello.lunchback.storeManagement.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    // 식당 주문 정보
    @GetMapping("/store/order")
    public ResponseEntity<? super GetStoreOrderResponseDto> storeOrder(@AuthenticationPrincipal String email){
        ResponseEntity<? super GetStoreOrderResponseDto> result = storeService.storeOrderList(email);
        return result;
    }

    // 식당 주문 정보 상세 보기
    @GetMapping("/store/order/{orderId}")
    public ResponseEntity<? super GetStoreOrderDetailResponseDto> orderDetail(@AuthenticationPrincipal String email, @PathVariable(name = "orderId") Integer orderId){
        ResponseEntity<? super GetStoreOrderDetailResponseDto> result = storeService.storeOrderDetail(email, orderId);
        return result;
    }

    // 식당 주문 취소 하기
    @DeleteMapping("/store/order/{orderId}")
    public ResponseEntity<? super DeleteStoreOrderResponseDto> orderDelete(@AuthenticationPrincipal String email, @PathVariable(name = "orderId") Integer orderId){
        ResponseEntity<? super DeleteStoreOrderResponseDto> result = storeService.orderDelete(email, orderId);
        return result;
    }

    @PostMapping("/store/create")
    public ResponseEntity<? super PostStoreCreateResponseDto> storeCreate(@AuthenticationPrincipal String email, @ModelAttribute PostStoreCreateRequestDto dto){
        ResponseEntity<? super PostStoreCreateResponseDto> result = storeService.storeCreate(email, dto);
        return result;
    }


}
