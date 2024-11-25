package hello.lunchback.login.service;

import hello.lunchback.login.dto.request.PostJoinRequestDto;
import hello.lunchback.login.dto.request.PostLoginRequestDto;
import hello.lunchback.login.dto.response.PostJoinResponseDto;
import hello.lunchback.login.dto.response.LoginResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface LoginService extends UserDetailsService {


    LoginResponse login(PostLoginRequestDto dto);

    PostJoinResponseDto join(PostJoinRequestDto dto);
}
