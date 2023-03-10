package me.kenux.travelog.domain.member.service;

import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.entity.RefreshTokenEntity;
import me.kenux.travelog.domain.member.entity.enums.UserRole;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserDetailsService userDetailsService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private RefreshTokenRepository refreshTokenRepository;
    @Mock
    JwtTokenIssuer jwtTokenIssuer;
    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("로그인 실패 - 패스워드 오류")
    void login_failed_wrong_password() {
        // given
        LoginRequest loginRequest = getLoginRequest();
        final UserDetails userDetails = getUserDetails();
        given(userDetailsService.loadUserByUsername(anyString())).willReturn(userDetails);
        given(passwordEncoder.matches(any(CharSequence.class), anyString())).willReturn(false);

        // when then
        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.AUTH_WRONG_PASSWORD.getMessage());
    }

    @Test
    @DisplayName("로그인 성공")
    void login_success() throws Exception {
        // given
        LoginRequest loginRequest = getLoginRequest();
        final UserDetails userDetails = getUserDetails();
        given(userDetailsService.loadUserByUsername(anyString())).willReturn(userDetails);
        given(passwordEncoder.matches(any(CharSequence.class), anyString())).willReturn(true);
        given(jwtTokenIssuer.createAccessToken(any(), any())).willReturn("accessToken");
        given(jwtTokenIssuer.createRefreshToken(any(), any())).willReturn("refreshToken");
        given(memberRepository.findById(any())).willReturn(Optional.of(Mockito.mock(Member.class)));

        // when
        final TokenInfo.Full tokenInfo = authService.login(loginRequest);

        // then
        assertThat(tokenInfo).isNotNull();
    }

    private UserDetails getUserDetails() {
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority("ADMIN"));

        return UserDetailsImpl.builder()
                .id(1L)
                .username("admin")
                .email("admin@test.com")
                .password("1")
                .authorities(roles)
                .build();
    }

    private LoginRequest getLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("admin@test.com");
        loginRequest.setPassword("1");
        return loginRequest;
    }

    @Test
    @DisplayName("refresh accessToken 성공")
    void refreshAccessToken_success() {
        // given
        Member member = Member.builder()
                .name("user")
                .email("user@test.com")
                .userRole(UserRole.USER).build();
        final RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity(member);
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest();
        refreshTokenRequest.setToken("oldToken");

        given(refreshTokenRepository.findByToken(any())).willReturn(Optional.of(refreshTokenEntity));
        given(jwtTokenIssuer.createAccessToken(anyString(), anyString())).willReturn("accessToken");

        // when
        final TokenInfo.AccessToken accessToken = authService.refreshAccessToken(refreshTokenRequest);

        // then
        assertThat(accessToken).isNotNull();
        verify(jwtTokenIssuer, times(1)).validateToken(any());
    }

    @Test
    @DisplayName("refresh accessToken 실패 - refresh token 없음")
    void refreshAccessToken_failed_notExistRefreshToken() {
        // given
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest();
        refreshTokenRequest.setToken("oldToken");

        given(refreshTokenRepository.findByToken(any())).willReturn(Optional.empty());

        // when then
        assertThatThrownBy(() -> authService.refreshAccessToken(refreshTokenRequest))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.AUTH_REFRESH_TOKEN_NOT_EXIST.getMessage());
    }

    @Test
    @DisplayName("refresh accessToken 실패 - refreshToken expired")
    void refreshAccessToken_failed_expiredRefreshToken() {
        // given
        Member member = Member.builder()
                .name("user")
                .email("user@test.com")
                .userRole(UserRole.USER).build();
        final RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity(member);
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest();
        refreshTokenRequest.setToken("oldToken");

        given(refreshTokenRepository.findByToken(any())).willReturn(Optional.of(refreshTokenEntity));
        willThrow(JwtExpiredException.class).given(jwtTokenIssuer).validateToken(refreshTokenEntity.getToken());

        // when then
        assertThatThrownBy(() -> authService.refreshAccessToken(refreshTokenRequest))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.AUTH_TOKEN_EXPIRED.getMessage());
    }
}