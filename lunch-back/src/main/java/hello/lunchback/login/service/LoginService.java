package hello.lunchback.login.service;

import hello.lunchback.login.dto.request.PostJoinRequestDto;
import hello.lunchback.login.dto.request.PostLoginRequestDto;
import hello.lunchback.login.dto.request.PutMemberUpdateRequestDto;
import hello.lunchback.login.dto.response.GetMemberResponseDto;
import hello.lunchback.login.dto.response.PostJoinResponseDto;
import hello.lunchback.login.dto.response.LoginResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface LoginService extends UserDetailsService {


    LoginResponse findMemberInfo(PostLoginRequestDto dto);

    PostJoinResponseDto join(PostJoinRequestDto dto);

    void updateMemberInfo(String email, PutMemberUpdateRequestDto dto);

    GetMemberResponseDto getMemberInfo(String email);
}
