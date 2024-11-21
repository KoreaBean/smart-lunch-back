package hello.lunchback.orderManagement.service;

import hello.lunchback.external.kakaoPay.dto.request.KakaopayRequestDto;
import hello.lunchback.external.kakaoPay.dto.response.KakaopayResponseDto;
import hello.lunchback.orderManagement.dto.request.PostOrderRequestDto;
import hello.lunchback.orderManagement.dto.response.GetOrderHistoryResponseDto;
import hello.lunchback.orderManagement.dto.response.PostOrderResponseDto;

public interface OrderService {
    GetOrderHistoryResponseDto orderHistory(String email);

    KakaopayResponseDto order(Integer storeId, String email, PostOrderRequestDto dto);
}
