package hello.lunchback.menuManagement.dto.response;

import hello.lunchback.common.response.ResponseCode;
import hello.lunchback.common.response.ResponseDto;
import hello.lunchback.common.response.ResponseMessage;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class PostMenuAddResponseDto extends ResponseDto {

    public PostMenuAddResponseDto() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }


    public static ResponseEntity<ResponseDto> notExistedStore(String message) {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_STORE, message);
        return ResponseEntity.status(HttpStatus.NOT_EXTENDED).body(result);
    }

    public static ResponseEntity<? super PostMenuAddResponseDto> success() {
        PostMenuAddResponseDto result = new PostMenuAddResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
