package hello.lunchback.orderManagement.service;

import hello.lunchback.external.kakaoPay.dto.request.KakaopayRequestDto;
import hello.lunchback.external.kakaoPay.dto.response.KakaopayResponseDto;
import hello.lunchback.orderManagement.dto.request.PostOrderRequestDto;
import hello.lunchback.orderManagement.dto.response.GetOrderHistoryDetailResponseDto;
import hello.lunchback.orderManagement.dto.response.GetOrderHistoryResponseDto;
import hello.lunchback.orderManagement.dto.response.PostOrderResponseDto;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    GetOrderHistoryResponseDto orderHistory(String email);

    KakaopayResponseDto order(Integer storeId, String email, PostOrderRequestDto dto);

    GetOrderHistoryDetailResponseDto orderHistoryDetail(Integer orderId, String email);
}
