package hello.lunchback.login.controller;

import hello.lunchback.login.dto.request.PostJoinRequestDto;
import hello.lunchback.login.dto.request.PostLoginRequestDto;
import hello.lunchback.login.dto.request.PutMemberUpdateRequestDto;
import hello.lunchback.login.dto.response.PostJoinResponseDto;
import hello.lunchback.login.dto.response.LoginResponse;
import hello.lunchback.login.dto.response.PutUpdateResponseDto;
import hello.lunchback.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public LoginResponse login(@RequestBody PostLoginRequestDto dto){

        LoginResponse login = loginService.login(dto);
        return login;
    }

    @PostMapping(value = "/join", produces = MediaType.APPLICATION_JSON_VALUE)
    public PostJoinResponseDto join(@RequestBody PostJoinRequestDto dto){
        PostJoinResponseDto join = loginService.join(dto);
        return join;
    }
//
//    // 회원 정보 수정
//    @PutMapping(value = "/update",produces = MediaType.APPLICATION_JSON_VALUE)
//    public void update (@AuthenticationPrincipal String email, PutMemberUpdateRequestDto dto){
//        loginService.update(email, dto);
//    }



}
