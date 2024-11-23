package hello.lunchback.login.dto.response;

import hello.lunchback.common.response.ResponseCode;
import hello.lunchback.common.response.ResponseDto;
import hello.lunchback.common.response.ResponseMessage;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@NoArgsConstructor
public class PostJoinResponseDto {

    private String code;

    public PostJoinResponseDto(String code)
    {
        this.code = code;
    }

    public static PostJoinResponseDto success(){
        PostJoinResponseDto result = new PostJoinResponseDto("SU");
        return result;
    }

}
