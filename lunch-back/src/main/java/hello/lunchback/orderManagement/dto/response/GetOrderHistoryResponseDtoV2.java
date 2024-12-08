package hello.lunchback.orderManagement.dto.response;

import hello.lunchback.common.response.ResponseCode;
import hello.lunchback.common.response.ResponseDto;
import hello.lunchback.common.response.ResponseMessage;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GetOrderHistoryResponseDtoV2 extends ResponseDto {

    List<Order> orderList = new ArrayList<>();

    public GetOrderHistoryResponseDtoV2() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);

    }

    public GetOrderHistoryResponseDtoV2(List<Order> orderList) {
        super(ResponseCode.SUCCESS,ResponseMessage.SUCCESS);
        this.orderList = orderList;
    }

    public static ResponseEntity<? super GetOrderHistoryResponseDtoV2> success(List<Order> orderList) {
        GetOrderHistoryResponseDtoV2 result = new GetOrderHistoryResponseDtoV2(orderList);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
