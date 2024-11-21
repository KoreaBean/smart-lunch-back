package hello.lunchback.external.kakaoPay.controller;

import hello.lunchback.external.kakaoPay.KakaoService;
import hello.lunchback.external.kakaoPay.dto.response.KakaopaySuccessResponseDto;
import hello.lunchback.orderManagement.dto.response.PostOrderResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kakaopay/payment")
@RequiredArgsConstructor
public class KakaopayController {

    private final KakaoService kakaoService;

    @GetMapping(value = "/success", produces = MediaType.APPLICATION_JSON_VALUE)
    public PostOrderResponseDto success(@RequestParam(name = "pg_token") String token){

        PostOrderResponseDto result = kakaoService.success(token);
        return result;
    }
}
