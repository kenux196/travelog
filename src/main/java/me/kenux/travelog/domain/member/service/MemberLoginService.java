package me.kenux.travelog.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.domain.member.dto.request.LoginRequest;
import me.kenux.travelog.domain.member.dto.request.LogoutRequest;
import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.entity.RefreshTokenEntity;
import me.kenux.travelog.domain.member.repository.MemberRepository;
import me.kenux.travelog.global.exception.CustomException;
import me.kenux.travelog.global.exception.ErrorCode;
import me.kenux.travelog.global.security.jwt.JwtTokenProvider;
import me.kenux.travelog.global.security.jwt.TokenInfo;
import me.kenux.travelog.global.security.repository.RefreshTokenRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;

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
    private final RefreshTokenRepository refreshTokenRepository;

    public void loginSuccessProcess(Long memberId) {
        final Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        member.successLogin();
    }

    @Transactional
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
        final TokenInfo tokenInfo = jwtTokenProvider.generateJwtToken(authentication);
        saveRefreshToken(tokenInfo, authentication);
        return tokenInfo;
    }

    private void saveRefreshToken(TokenInfo tokenInfo, Authentication authentication) {
        final RefreshTokenEntity entity = new RefreshTokenEntity(tokenInfo.getRefreshToken(), authentication.getName());
        refreshTokenRepository.save(entity);
    }

    @Transactional
    public void logout(LogoutRequest request) {
        // remove refresh token
        final RefreshTokenEntity refreshToken = refreshTokenRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new BadCredentialsException("Not founded refresh token for " + request.getUsername()));
        refreshTokenRepository.delete(refreshToken);
    }
}
