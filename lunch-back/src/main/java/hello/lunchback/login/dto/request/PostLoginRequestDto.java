package hello.lunchback.login.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostLoginRequestDto {

    private String email;
    private String password;

}
