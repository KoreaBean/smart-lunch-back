package hello.lunchback.login.service.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hello.lunchback.common.config.BcyptEncoder;
import hello.lunchback.login.dto.request.PostJoinRequestDto;
import hello.lunchback.login.dto.request.PostLoginRequestDto;
import hello.lunchback.login.dto.response.PostJoinResponseDto;
import hello.lunchback.login.dto.response.PostLoginResponseDto;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
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
    public ResponseEntity<? super PostLoginResponseDto> login(PostLoginRequestDto dto) {
        log.info("LoginServiceImpl : login : start");
        String token = null;
        try {
            // 사용자 인증
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
            // 사용자 추출
            UserDetails principal = (UserDetails) authenticate.getPrincipal();
            // JWT 생성
            token = jwtProvider.create(principal);
        }catch (UsernameNotFoundException e) {
            log.info("LoginServiceImpl : login : error : UsernameNotFoundException");
            return PostLoginResponseDto.notExistedUser();
        }catch (BadCredentialsException e){
            log.info("LoginServiceImpl : login : error : BadCredentialsException");
            return PostLoginResponseDto.notExistedUser();
        }
        log.info("LoginServiceImpl : login : complete");
        return PostLoginResponseDto.success(token);

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
                return PostJoinResponseDto.notExisted("이미 존재하는 email 입니다.");
            }
             role = roleRepository.findByRoleName(RoleType.ROLE_consumer.toString());

            String encodePassword = passwordEncoder.passwordEncoder().encode(dto.getPassword());
            member.createMember(dto,encodePassword);
            member.addRole(role);
            memberRepository.save(member);
        }catch (Exception e) {
            return PostJoinResponseDto.databaseError();
        }
        // 2. password 암호화
        // 3. 저장
        log.info("LoginServiceImpl : join : complete");
        return PostJoinResponseDto.success();
    }

    private Boolean duplicatedEmail(PostJoinRequestDto dto) {

        Optional<MemberEntity> memberEntity = memberRepository.findByMemberEmail(dto.getEmail());
        if (memberEntity.isEmpty()){
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
