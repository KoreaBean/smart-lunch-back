package hello.lunchback.orderManagement.controller;

import hello.lunchback.external.kakaoPay.dto.request.KakaopayRequestDto;
import hello.lunchback.external.kakaoPay.dto.response.KakaopayResponseDto;
import hello.lunchback.orderManagement.dto.request.PostOrderRequestDto;
import hello.lunchback.orderManagement.dto.response.GetOrderHistoryResponseDto;
import hello.lunchback.orderManagement.dto.response.PostOrderResponseDto;
import hello.lunchback.orderManagement.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 주문 정보 저장

    // 주문 내역 조회
    @PreAuthorize("hasRole('ROLE_consumer') or hasRole('ROLE_admin')")
    @GetMapping("/order/history")
    public GetOrderHistoryResponseDto getOrderHistoryList(@AuthenticationPrincipal String email){
        orderService.orderHistory(email);
        return null;
    }

    // 대기 및 혼잡도 계산

    // 주문 하기
    //@PreAuthorize("hasRole('ROLE_consumer') or hasRole('ROLE_admin')")
    @PostMapping(value = "/order/{storeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public KakaopayResponseDto order(@AuthenticationPrincipal String email, @PathVariable(name = "storeId")Integer storeId,
                                     @RequestBody PostOrderRequestDto dto){
        KakaopayResponseDto result = orderService.order(storeId, email, dto);
        return result;
    }

    // 주문 취소

    // 주문 정보 삭제

    // 주문 알림

    // 결제하기



}
