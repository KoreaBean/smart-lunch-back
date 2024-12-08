package hello.lunchback.menuManagement.dto.response;

import hello.lunchback.common.response.ResponseCode;
import hello.lunchback.common.response.ResponseDto;
import hello.lunchback.common.response.ResponseMessage;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@Getter
@Setter
@NoArgsConstructor
public class PostMenuAddResponseDto  {


    public static PostMenuAddResponseDto success() {
        PostMenuAddResponseDto result = new PostMenuAddResponseDto();
        return result;
    }
}
