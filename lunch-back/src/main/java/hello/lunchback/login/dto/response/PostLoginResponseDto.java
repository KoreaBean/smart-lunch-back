package hello.lunchback.login.dto.response;

import hello.lunchback.common.response.ResponseCode;
import hello.lunchback.common.response.ResponseDto;
import hello.lunchback.common.response.ResponseMessage;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@NoArgsConstructor
public class PostLoginResponseDto  {

    private String code;
    private String token;
    private Boolean isMember;




    public PostLoginResponseDto(String code,String token, Boolean isMember) {
        this.token = token;
        this.code = code;
        this.isMember = isMember;
    }

    public static PostLoginResponseDto success(String token, Boolean isMember){
        PostLoginResponseDto responseDto = new PostLoginResponseDto("SU",token, isMember);
        return responseDto;
    }


}
