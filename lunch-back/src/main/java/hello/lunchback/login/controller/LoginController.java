package hello.lunchback.login.controller;

import hello.lunchback.login.dto.request.PostJoinRequestDto;
import hello.lunchback.login.dto.request.PostLoginRequestDto;
import hello.lunchback.login.dto.response.PostJoinResponseDto;
import hello.lunchback.login.dto.response.PostLoginResponseDto;
import hello.lunchback.login.service.LoginService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
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

    @PostMapping("/login")
    public ResponseEntity<? super PostLoginResponseDto> login(@RequestBody PostLoginRequestDto dto){

        ResponseEntity<? super PostLoginResponseDto> login = loginService.login(dto);
        return login;
    }

    @PostMapping("/join")
    public ResponseEntity<? super PostJoinResponseDto> join(@RequestBody PostJoinRequestDto dto){
        ResponseEntity<? super PostJoinResponseDto> join = loginService.join(dto);
        return join;
    }
}
