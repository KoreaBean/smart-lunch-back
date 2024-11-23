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
public class PostMenuUpdateResponseDto {


    private String code;

    public PostMenuUpdateResponseDto(String code) {
        this.code = code;
    }


    public static PostMenuUpdateResponseDto success() {
        PostMenuUpdateResponseDto result = new PostMenuUpdateResponseDto("SU");
        return  result;
    }
}
