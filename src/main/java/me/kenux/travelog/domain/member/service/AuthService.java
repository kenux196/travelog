package me.kenux.travelog.domain.member.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.entity.RefreshTokenEntity;
import me.kenux.travelog.domain.member.repository.MemberRepository;
import me.kenux.travelog.domain.member.repository.RefreshTokenRepository;
import me.kenux.travelog.domain.member.service.dto.TokenInfo;
import me.kenux.travelog.domain.member.service.dto.UserDetailsImpl;
import me.kenux.travelog.domain.member.service.dto.request.LoginRequest;
import me.kenux.travelog.domain.member.service.dto.request.RefreshTokenRequest;
import me.kenux.travelog.global.exception.CustomException;
import me.kenux.travelog.global.exception.ErrorCode;
import me.kenux.travelog.global.exception.JwtExpiredException;
import me.kenux.travelog.global.security.jwt.JwtTokenIssuer;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AuthService {

    private final MemberRepository memberRepository;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenIssuer jwtTokenIssuer;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public TokenInfo.Full login(LoginRequest request) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
            log.error("{} 비밀번호 오류", request.getUsername());
            throw new CustomException(ErrorCode.AUTH_WRONG_PASSWORD);
        }
        log.info("{}, 인증 확인됨", request.getUsername());
        final String authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        final String accessToken = jwtTokenIssuer.createAccessToken(userDetails.getUsername(), authorities);
        final String refreshToken = jwtTokenIssuer.createRefreshToken(userDetails.getUsername(), authorities);
        saveRefreshToken(refreshToken, ((UserDetailsImpl) userDetails).getId());

        return TokenInfo.Full.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .grantType("Bearer")
                .role(authorities)
                .build();
    }

    public TokenInfo.AccessToken refreshAccessToken(RefreshTokenRequest request) {
        try {
            jwtTokenIssuer.validateToken(request.getToken());
            final RefreshTokenEntity refreshToken = refreshTokenRepository.findByToken(request.getToken())
                    .orElseThrow(() -> new CustomException(ErrorCode.AUTH_REFRESH_TOKEN_NOT_EXIST));
            final Member member = refreshToken.getMember();
            final String accessToken = jwtTokenIssuer.createAccessToken(member.getEmail(), member.getUserRole().toString());
            return TokenInfo.AccessToken.builder()
                    .accessToken(accessToken)
                    .build();
        } catch (JwtExpiredException e) {
            throw new CustomException(ErrorCode.AUTH_TOKEN_EXPIRED);
        }
    }

    private void saveRefreshToken(String refreshToken, Long memberId) {
        memberRepository.findById(memberId)
                .ifPresent(member -> {
                    final RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findByMemberId(memberId)
                            .orElse(new RefreshTokenEntity(member));
                    refreshTokenEntity.updateToken(refreshToken);
                    refreshTokenRepository.save(refreshTokenEntity);
                });
    }

    @Transactional
    public void logout(HttpServletRequest request) {
        final String accessToken = resolveToken(request);
        final String username = jwtTokenIssuer.getUserNameFromJwtToken(accessToken);
        final Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new CustomException(ErrorCode.AUTH_UNAUTHORIZED));

        refreshTokenRepository.deleteByMember(member.getId());
        // TODO - redis cache 이용하여 로그아웃된 토큰에 대한 처리 필요. 혹은 스프링 자체 캐싱 사용? 2023-01-30 skyun
        SecurityContextHolder.clearContext();
    }

    private String resolveToken(HttpServletRequest request) {
        final String AUTHORIZATION_HEADER = "Authorization";
        final String BEARER_PREFIX = "Bearer ";
        final String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }
}
