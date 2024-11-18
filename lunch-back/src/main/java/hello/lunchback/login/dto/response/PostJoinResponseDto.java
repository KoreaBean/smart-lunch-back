package hello.lunchback.login.dto.response;

import hello.lunchback.common.response.ResponseCode;
import hello.lunchback.common.response.ResponseDto;
import hello.lunchback.common.response.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PostJoinResponseDto extends ResponseDto {

    public PostJoinResponseDto(){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    public static ResponseEntity<? super PostJoinResponseDto> success(){
        PostJoinResponseDto result = new PostJoinResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto>notExisted(String message){
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, message);
        return ResponseEntity.status(HttpStatus.NOT_EXTENDED).body(result);
    }

}
