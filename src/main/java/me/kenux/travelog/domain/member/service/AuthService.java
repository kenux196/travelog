package me.kenux.travelog.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.domain.member.dto.request.LoginRequest;
import me.kenux.travelog.domain.member.dto.request.RefreshTokenRequest;
import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.entity.RefreshTokenEntity;
import me.kenux.travelog.domain.member.repository.MemberRepository;
import me.kenux.travelog.global.exception.CustomException;
import me.kenux.travelog.global.exception.ErrorCode;
import me.kenux.travelog.global.security.UserDetailsImpl;
import me.kenux.travelog.global.security.jwt.JwtTokenProvider;
import me.kenux.travelog.global.security.jwt.JwtValidationResult;
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

import java.util.Objects;
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

    public TokenInfo refreshAccessToken(RefreshTokenRequest request) {
        final RefreshTokenEntity refreshToken = refreshTokenRepository.findByToken(request.getToken())
            .orElseThrow(() -> new CustomException(ErrorCode.AUTH_REFRESH_TOKEN_NOT_EXIST));

        final JwtValidationResult result = jwtTokenProvider.validateToken(refreshToken.getToken());

        if (JwtValidationResult.VALID.equals(result)) {
            final Member member = refreshToken.getMember();
            final String accessToken = jwtTokenProvider.createAccessToken(member.getEmail(), member.getUserRole().toString());
            return TokenInfo.builder()
                .accessToken(accessToken)
                .build();
        } else if (JwtValidationResult.EXPIRED.equals(result)) {
            // TODO - neet relogin 2023-01-30 skyun
            throw new RuntimeException("Need re-login");
        } else {
            // TODO - error 2023-01-30 skyun
            throw new RuntimeException("error");
        }
    }

    private void saveRefreshToken(String refreshToken, Long memberId) {
        final Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(ErrorCode.AUTH_MEMBER_NOT_EXIST));
        final RefreshTokenEntity entity = new RefreshTokenEntity(refreshToken, member);
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

        // TODO - redis cache 이용하여 로그아웃된 토큰에 대한 처리 필요. 혹은 스프링 자체 캐싱 사용? 2023-01-30 skyun
    }
}
