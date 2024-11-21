package hello.lunchback.external.kakaoPay.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KakaopayResponseDto {

    // 결제 고유 번호, 20자
    private String tid;
    // 요청한 클라이언트가 모바일 앱인 경우
    private String next_redirect_app_url;
    // 요청한 클라이언트가 모바일 웹인 경우
    private String next_redirect_mobile_url;
    // 요청한 클라이언트가 pc 인 경우
    private String next_redirect_pc_url;
    // 카카오페이 결제 화면으로 이동하는 내부 서비스용
    private String android_app_scheme;
    // 카카오페이 결제 화면으로 이동하는 IOS 앱 스킴 - 내부 서비스용
    private String ios_app_scheme;
    private String created_at;

}
