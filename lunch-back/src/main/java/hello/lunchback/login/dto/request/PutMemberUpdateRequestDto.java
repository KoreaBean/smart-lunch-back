package hello.lunchback.login.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PutMemberUpdateRequestDto {

    private Long memberId;
    private String memberName;
    private String memberPhone;
    private String password;

}
