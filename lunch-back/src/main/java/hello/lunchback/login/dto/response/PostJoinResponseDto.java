package hello.lunchback.login.dto.response;

import hello.lunchback.common.response.ResponseCode;
import hello.lunchback.common.response.ResponseDto;
import hello.lunchback.common.response.ResponseMessage;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
public class PostJoinResponseDto extends ResponseDto{

    public PostJoinResponseDto() {
        super(ResponseCode.SUCCESS,ResponseMessage.SUCCESS);
    }

    public static ResponseEntity<? super PostJoinResponseDto> success(){
        PostJoinResponseDto result = new PostJoinResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<? super PostJoinResponseDto> duplicatedEmail() {
        ResponseDto result = new ResponseDto(ResponseCode.DUPLICATE_EMAIL, ResponseMessage.DUPLICATE_EMAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
