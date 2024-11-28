package hello.lunchback.login.dto.request;

import lombok.Data;

@Data
public class PutMemberUpdateRequestDto {

    private Long memberId;
    private String memberName;
    private String memberPhone;
    private String password;

}
