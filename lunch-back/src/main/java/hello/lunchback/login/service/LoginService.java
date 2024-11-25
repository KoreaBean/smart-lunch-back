package hello.lunchback.login.service;

import hello.lunchback.login.dto.request.PostJoinRequestDto;
import hello.lunchback.login.dto.request.PostLoginRequestDto;
import hello.lunchback.login.dto.request.PutMemberUpdateRequestDto;
import hello.lunchback.login.dto.response.GetMemberResponseDto;
import hello.lunchback.login.dto.response.PostJoinResponseDto;
import hello.lunchback.login.dto.response.LoginResponse;
import hello.lunchback.login.dto.response.PutUpdateResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface LoginService extends UserDetailsService {


    LoginResponse login(PostLoginRequestDto dto);

    PostJoinResponseDto join(PostJoinRequestDto dto);

    void update(String email, PutMemberUpdateRequestDto dto);

    GetMemberResponseDto getMember(String email);
}
