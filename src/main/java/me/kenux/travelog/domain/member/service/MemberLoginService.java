package me.kenux.travelog.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.domain.member.dto.request.LoginRequest;
import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.repository.MemberRepository;
import me.kenux.travelog.global.exception.CustomException;
import me.kenux.travelog.global.exception.ErrorCode;
import me.kenux.travelog.global.security.jwt.JwtTokenProvider;
import me.kenux.travelog.global.security.jwt.TokenInfo;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberLoginService {

    private final MemberRepository memberRepository;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    public void loginSuccessProcess(Long memberId) {
        final Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        member.successLogin();
    }

    public TokenInfo login(LoginRequest request) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
            log.error("{} 비밀번호 오류", request.getUsername());
            throw new BadCredentialsException("Wrong password for [" + request.getUsername() + "]");
        }
        log.info("{}, 인증 확인됨", request.getUsername());
        final UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

        final Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return jwtTokenProvider.generateJwtToken(authentication);
    }
}
