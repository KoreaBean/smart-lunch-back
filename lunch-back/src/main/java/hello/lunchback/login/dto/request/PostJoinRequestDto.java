package hello.lunchback.login.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
public class PostJoinRequestDto {
    private String name;
    private String phone;
    private String email;
    private String password;
}
