package hello.lunchback.menuManagement.dto.response;

import hello.lunchback.common.response.ResponseCode;
import hello.lunchback.common.response.ResponseDto;
import hello.lunchback.common.response.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PostMenuUpdateResponseDto extends ResponseDto {


    public PostMenuUpdateResponseDto() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }


    public static ResponseEntity<? super PostMenuUpdateResponseDto> success() {
        PostMenuUpdateResponseDto result = new PostMenuUpdateResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
