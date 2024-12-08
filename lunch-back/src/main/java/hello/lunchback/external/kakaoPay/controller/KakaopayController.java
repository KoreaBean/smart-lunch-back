package hello.lunchback.external.kakaoPay.controller;

import hello.lunchback.external.kakaoPay.KakaoService;
import hello.lunchback.external.kakaoPay.dto.response.KakaopayFailResponseDto;
import hello.lunchback.external.kakaoPay.dto.response.KakaopaySuccessResponseDto;
import hello.lunchback.orderManagement.dto.response.PostOrderResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/kakaopay/payment")
@RequiredArgsConstructor
public class KakaopayController {

    private final KakaoService kakaoService;

    @GetMapping(value = "/success")
    public void success(@RequestParam(name = "pg_token") String token, HttpServletResponse response) throws IOException {

       kakaoService.success(token , response);
    }

    @GetMapping("/fail")
    public void fail(@RequestBody KakaopayFailResponseDto dto){
        kakaoService.fail(dto);
    }
    @GetMapping("/cancel")
    public void cancel(){

    }



}
