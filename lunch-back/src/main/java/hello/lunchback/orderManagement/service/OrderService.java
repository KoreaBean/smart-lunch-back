package hello.lunchback.orderManagement.service;

import hello.lunchback.external.kakaoPay.dto.response.KakaopayResponseDto;
import hello.lunchback.orderManagement.dto.request.PostOrderRequestDto;
import hello.lunchback.orderManagement.dto.response.GetOrderHistoryDetailResponseDto;
import hello.lunchback.orderManagement.dto.response.GetOrderHistoryResponseDto;
import hello.lunchback.orderManagement.dto.response.GetOrderHistoryResponseDtoV2;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    GetOrderHistoryResponseDto getOrderHistoryList(String email);

    KakaopayResponseDto order(Integer storeId, String email, PostOrderRequestDto dto);

    GetOrderHistoryDetailResponseDto orderHistoryDetail(Integer orderId, String email);

    ResponseEntity<? super GetOrderHistoryResponseDtoV2> getOrderHistoryV2(String email);
}
