package hello.lunchback.login.service.impl;

import hello.lunchback.common.config.BcyptEncoder;
import hello.lunchback.login.dto.request.PostJoinRequestDto;
import hello.lunchback.login.dto.request.PostLoginRequestDto;
import hello.lunchback.login.dto.request.PutMemberUpdateRequestDto;
import hello.lunchback.login.dto.response.GetMemberResponseDto;
import hello.lunchback.login.dto.response.PostJoinResponseDto;
import hello.lunchback.login.dto.response.LoginResponse;
import hello.lunchback.login.dto.response.PutUpdateResponseDto;
import hello.lunchback.login.entity.MemberEntity;
import hello.lunchback.login.entity.RoleEntity;
import hello.lunchback.login.entity.RoleType;
import hello.lunchback.login.provider.JwtProvider;
import hello.lunchback.login.repository.MemberRepository;
import hello.lunchback.login.repository.RoleRepository;
import hello.lunchback.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final BcyptEncoder passwordEncoder;

    // 로그인
    @Override
    @Transactional
    public LoginResponse findMemberInfo(PostLoginRequestDto dto) {
        log.info("LoginServiceImpl : login : start");
        String token = null;
        Boolean isMember = true;
        try {
            // 사용자 인증
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
            // 사용자 추출
            UserDetails principal = (UserDetails) authenticate.getPrincipal();
            // JWT 생성
            token = jwtProvider.create(principal);
            for (GrantedAuthority authority : authenticate.getAuthorities()) {
                if (authority.getAuthority().equals(RoleType.restaurant.toString())){
                    isMember = false;
                }
            }

            log.info("create Token : {}",token);
        }catch (UsernameNotFoundException e) {
            log.info("LoginServiceImpl : login : error : UsernameNotFoundException");
        }catch (BadCredentialsException e){
            log.info("LoginServiceImpl : login : error : BadCredentialsException");
        }
        log.info("LoginServiceImpl : login : complete");
        return LoginResponse.success(token,isMember);

    }

    // 회원가입
    @Override
    @Transactional
    public ResponseEntity<? super PostJoinResponseDto> join(PostJoinRequestDto dto) {
        log.info("LoginServiceImpl : join : start");
        MemberEntity member = new MemberEntity();
        RoleEntity role = new RoleEntity();
        // 1. 이메일 duplicated
        try {
            Boolean isDuplicated = duplicatedEmail(dto);
            if (isDuplicated){
                log.info("LoginServiceImpl : join : isDuplicated : True");
                return PostJoinResponseDto.duplicatedEmail();
            }
             role = roleRepository.findByRoleName(RoleType.consumer.toString());

            String encodePassword = passwordEncoder.passwordEncoder().encode(dto.getPassword());
            member.createMember(dto,encodePassword);
            member.addRole(role);
            memberRepository.save(member);
        }catch (Exception e) {
            log.info("LoginServiceImpl : join : error ={}");
            e.printStackTrace();
        }
        // 2. password 암호화
        // 3. 저장
        log.info("LoginServiceImpl : join : complete");
        return PostJoinResponseDto.success();
    }

    @Override
    @Transactional
    public void updateMemberInfo(String email, PutMemberUpdateRequestDto dto) {

        try {
            MemberEntity member = memberRepository.findByMemberEmail(email)
                    .orElse(null);
            encodePassword(dto);
            member.update(dto);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void encodePassword(PutMemberUpdateRequestDto dto) {
        String encode = passwordEncoder.passwordEncoder().encode(dto.getPassword());
        dto.setPassword(encode);
    }

    @Override
    public GetMemberResponseDto getMemberInfo(String email) {
        MemberEntity member = new MemberEntity();
        try {

            member = memberRepository.findByMemberEmail(email)
                    .orElse(null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return GetMemberResponseDto.succss(member);

    }

    private Boolean duplicatedEmail(PostJoinRequestDto dto) {
        MemberEntity member = memberRepository.findByMemberEmail(dto.getEmail())
                .orElse(null);

        if (member == null) {
            return false;
        }
        return true;
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("LoginServiceImpl : loadUserByUsername : start");
        MemberEntity member = memberRepository.findByMemberEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        List<SimpleGrantedAuthority> roles = member.getRoles()
                .stream()
                .map(RoleEntity -> new SimpleGrantedAuthority(RoleEntity.getRoleName()))
                .toList();
        log.info("loginServiceImpl : loginUserByUsername : complete");
        return new User(member.getMemberEmail(),member.getMemberPassword(),roles);

    }
}
