package hello.lunchback.orderManagement.service;

import hello.lunchback.orderManagement.dto.request.PostOrderRequestDto;
import hello.lunchback.orderManagement.dto.response.GetOrderHistoryResponseDto;
import hello.lunchback.orderManagement.dto.response.PostOrderResponseDto;

public interface OrderService {
    GetOrderHistoryResponseDto orderHistory(String email);

    PostOrderResponseDto order(Integer storeId, String email, PostOrderRequestDto dto);
}
