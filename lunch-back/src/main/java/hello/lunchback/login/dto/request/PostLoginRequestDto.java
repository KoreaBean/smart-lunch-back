package hello.lunchback.login.dto.request;

import lombok.Data;

@Data
public class PostLoginRequestDto {

    private String email;
    private String password;

}
