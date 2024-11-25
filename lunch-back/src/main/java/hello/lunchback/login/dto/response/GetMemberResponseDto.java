package hello.lunchback.login.dto.response;

import hello.lunchback.login.entity.MemberEntity;
import lombok.Data;

@Data
public class GetMemberResponseDto {

    private Long memberId;
    private String memberName;
    private String memberPhone;
    private String memberEmail;

    public GetMemberResponseDto(MemberEntity member) {
        this.memberId = member.getMemberId();
        this.memberName = member.getMemberName();
        this.memberPhone = member.getMemberPhone();
        this.memberEmail = member.getMemberEmail();
    }

    public static GetMemberResponseDto succss(MemberEntity member) {
        GetMemberResponseDto result = new GetMemberResponseDto(member);
        return result;
    }
}
