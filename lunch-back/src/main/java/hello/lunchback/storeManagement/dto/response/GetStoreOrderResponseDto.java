package hello.lunchback.storeManagement.dto.response;

import hello.lunchback.common.response.ResponseCode;
import hello.lunchback.common.response.ResponseDto;
import hello.lunchback.common.response.ResponseMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Data
public class GetStoreOrderResponseDto extends ResponseDto {
    private List<OrderItem> orderItemList = new ArrayList<>();


    public GetStoreOrderResponseDto(List<OrderItem> orderItemList) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.orderItemList = orderItemList;
    }

    public static ResponseEntity<? super GetStoreOrderResponseDto> notExistedUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.NOT_EXTENDED).body(result);
    }

    public static ResponseEntity<? super GetStoreOrderResponseDto> notExistedStore() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_STORE, ResponseMessage.NOT_EXISTED_STORE);
        return ResponseEntity.status(HttpStatus.NOT_EXTENDED).body(result);
    }

    public static ResponseEntity<? super GetStoreOrderResponseDto> notExistedOrderList() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_ORDER_LIST, ResponseMessage.NOT_EXISTED_ORDER_LIST);
        return ResponseEntity.status(HttpStatus.NOT_EXTENDED).body(result);
    }

    public static ResponseEntity<? super GetStoreOrderResponseDto> success(List<OrderItem> orderItemList) {
        GetStoreOrderResponseDto result = new GetStoreOrderResponseDto(orderItemList);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
