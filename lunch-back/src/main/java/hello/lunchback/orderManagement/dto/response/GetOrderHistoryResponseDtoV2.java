package hello.lunchback.orderManagement.dto.response;

import hello.lunchback.common.response.ResponseCode;
import hello.lunchback.common.response.ResponseDto;
import hello.lunchback.common.response.ResponseMessage;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class GetOrderHistoryResponseDtoV2 extends ResponseDto {

    List<Order> orderList = new ArrayList<>();

    public GetOrderHistoryResponseDtoV2() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);

    }
}
