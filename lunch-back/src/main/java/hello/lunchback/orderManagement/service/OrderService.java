package hello.lunchback.orderManagement.service;

import hello.lunchback.external.kakaoPay.dto.response.KakaopayResponseDto;
import hello.lunchback.orderManagement.dto.request.PostOrderRequestDto;
import hello.lunchback.orderManagement.dto.response.GetOrderHistoryDetailResponseDto;
import hello.lunchback.orderManagement.dto.response.GetOrderHistoryResponseDto;

public interface OrderService {
    GetOrderHistoryResponseDto getOrderHistoryList(String email);

    KakaopayResponseDto order(Integer storeId, String email, PostOrderRequestDto dto);

    GetOrderHistoryDetailResponseDto orderHistoryDetail(Integer orderId, String email);
}
