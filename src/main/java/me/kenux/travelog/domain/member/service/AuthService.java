package me.kenux.travelog.domain.member.service;

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
//        final UsernamePasswordAuthenticationToken authenticationToken =
//            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

//        final Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
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
        final RefreshTokenEntity refreshToken = refreshTokenRepository.findByToken(request.getToken())
            .orElseThrow(() -> new CustomException(ErrorCode.AUTH_REFRESH_TOKEN_NOT_EXIST));

        try {
            jwtTokenIssuer.validateToken(refreshToken.getToken());
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
        final Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(ErrorCode.AUTH_UNREGISTERED_MEMBER));

        final RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findByMemberId(memberId)
                .orElse(new RefreshTokenEntity(member));
        refreshTokenEntity.updateToken(refreshToken);
        refreshTokenRepository.save(refreshTokenEntity);
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
