package hello.lunchback.login.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponse {

    private String token;
    private Boolean isMember;




    public LoginResponse(String token, Boolean isMember) {
        this.token = token;
        this.isMember = isMember;
    }

    public static LoginResponse success(String token, Boolean isMember){
        LoginResponse responseDto = new LoginResponse(token, isMember);
        return responseDto;
    }


}
