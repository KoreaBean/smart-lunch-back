package hello.lunchback.login.service;

import hello.lunchback.login.dto.request.PostJoinRequestDto;
import hello.lunchback.login.dto.request.PostLoginRequestDto;
import hello.lunchback.login.dto.response.PostJoinResponseDto;
import hello.lunchback.login.dto.response.PostLoginResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface LoginService extends UserDetailsService {


    ResponseEntity<? super PostLoginResponseDto> login(PostLoginRequestDto dto);

    ResponseEntity<? super PostJoinResponseDto> join(PostJoinRequestDto dto);
}
