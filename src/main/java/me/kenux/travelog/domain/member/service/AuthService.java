package me.kenux.travelog.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.domain.member.dto.request.LoginRequest;
import me.kenux.travelog.domain.member.entity.RefreshTokenEntity;
import me.kenux.travelog.domain.member.repository.MemberRepository;
import me.kenux.travelog.global.exception.CustomException;
import me.kenux.travelog.global.exception.ErrorCode;
import me.kenux.travelog.global.security.UserDetailsImpl;
import me.kenux.travelog.global.security.jwt.JwtTokenProvider;
import me.kenux.travelog.global.security.jwt.TokenInfo;
import me.kenux.travelog.global.security.repository.RefreshTokenRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AuthService {

    private final MemberRepository memberRepository;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public TokenInfo login(LoginRequest request) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
            log.error("{} 비밀번호 오류", request.getUsername());
            throw new CustomException(ErrorCode.AUTH_WRONG_PASSWORD);
        }
        log.info("{}, 인증 확인됨", request.getUsername());
        final UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

        final Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        final String authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));
        final String accessToken = jwtTokenProvider.createAccessToken(authentication.getName(), authorities);
        final String refreshToken = jwtTokenProvider.createRefreshToken();
        saveRefreshToken(refreshToken, ((UserDetailsImpl) userDetails).getId());

        return TokenInfo.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .grantType("Bearer")
            .role(authorities)
            .build();
    }

    private void saveRefreshToken(String refreshToken, Long memberId) {
        final RefreshTokenEntity entity = new RefreshTokenEntity(refreshToken, memberId);
        refreshTokenRepository.save(entity);
    }

    @Transactional
    public void logout() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final UserDetailsImpl details = (UserDetailsImpl) authentication.getPrincipal();

        // remove refresh token
        final RefreshTokenEntity refreshToken = refreshTokenRepository.findByMemberId(details.getId())
            .orElseThrow(() -> new BadCredentialsException("Not founded refresh token for " + details.getId()));
        refreshTokenRepository.delete(refreshToken);
    }

    public TokenInfo refresh(String refreshToken) {
        // TODO - refresh token 2023-01-24 sky
        // 1. refresh token 기간 만료 검증 -> 만료 시 exception -> 다시 로그인해야 한다.
        // 2. refresh token 기간 유효 -> access token 재발급
        return TokenInfo.builder().build();

    }
}
