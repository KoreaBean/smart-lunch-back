package hello.lunchback.login.controller;

import hello.lunchback.login.dto.request.PostJoinRequestDto;
import hello.lunchback.login.dto.request.PostLoginRequestDto;
import hello.lunchback.login.dto.response.PostJoinResponseDto;
import hello.lunchback.login.dto.response.PostLoginResponseDto;
import hello.lunchback.login.service.LoginService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public PostLoginResponseDto login(@RequestBody PostLoginRequestDto dto){

        PostLoginResponseDto login = loginService.login(dto);
        return login;
    }

    @PostMapping(value = "/join", produces = MediaType.APPLICATION_JSON_VALUE)
    public PostJoinResponseDto join(@RequestBody PostJoinRequestDto dto){
        PostJoinResponseDto join = loginService.join(dto);
        return join;
    }
}
