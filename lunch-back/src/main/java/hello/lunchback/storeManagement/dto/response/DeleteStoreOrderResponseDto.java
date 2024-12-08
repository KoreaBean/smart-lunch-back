package hello.lunchback.storeManagement.dto.response;

import hello.lunchback.common.response.ResponseCode;
import hello.lunchback.common.response.ResponseDto;
import hello.lunchback.common.response.ResponseMessage;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class DeleteStoreOrderResponseDto extends ResponseDto {


    public DeleteStoreOrderResponseDto() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    public static ResponseEntity<? super DeleteStoreOrderResponseDto> notExistedOrder() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_ORDER_LIST, ResponseMessage.NOT_EXISTED_ORDER_LIST);
        return ResponseEntity.status(HttpStatus.NOT_EXTENDED).body(result);
    }

    public static ResponseEntity<? super DeleteStoreOrderResponseDto> success() {
        DeleteStoreOrderResponseDto result = new DeleteStoreOrderResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
