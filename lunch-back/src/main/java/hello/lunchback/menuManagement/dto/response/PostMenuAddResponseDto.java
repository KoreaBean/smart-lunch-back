package hello.lunchback.menuManagement.dto.response;

import hello.lunchback.common.response.ResponseCode;
import hello.lunchback.common.response.ResponseDto;
import hello.lunchback.common.response.ResponseMessage;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@Data
@NoArgsConstructor
public class PostMenuAddResponseDto  {


    private String code;

    public PostMenuAddResponseDto(String code) {
        this.code = code;
    }


    public static PostMenuAddResponseDto success() {
        PostMenuAddResponseDto result = new PostMenuAddResponseDto("SU");
        return result;
    }
}
