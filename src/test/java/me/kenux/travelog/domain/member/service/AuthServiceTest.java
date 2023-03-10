package me.kenux.travelog.domain.member.service;

import jakarta.servlet.http.HttpServletRequest;
import me.kenux.travelog.domain.member.repository.RefreshTokenRepository;
import me.kenux.travelog.domain.member.service.dto.TokenInfo;
import me.kenux.travelog.domain.member.service.dto.UserDetailsImpl;
import me.kenux.travelog.domain.member.service.dto.request.LoginRequest;
import me.kenux.travelog.domain.member.service.dto.request.ReissueTokenRequest;
import me.kenux.travelog.global.exception.CustomException;
import me.kenux.travelog.global.exception.ErrorCode;
import me.kenux.travelog.global.exception.JwtExpiredException;
import me.kenux.travelog.global.security.jwt.JwtTokenIssuer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
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
        LoginRequest mockLoginRequest = mock(LoginRequest.class);
        UserDetails mockUserDetails = mock(UserDetails.class);
        given(userDetailsService.loadUserByUsername(any())).willReturn(mockUserDetails);
        given(passwordEncoder.matches(any(), any())).willReturn(true);
        given(jwtTokenIssuer.createAccessToken(any(), any())).willReturn("accessToken");
        given(jwtTokenIssuer.createRefreshToken(any(), any())).willReturn("refreshToken");

        // when
        final TokenInfo.Full tokenInfo = authService.login(mockLoginRequest);

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
    @DisplayName("정상 refresh token 이지만, 서버에 존재하지 않으면 access token 재발급 실패한다.")
    void refreshAccessToken_failed_notExistRefreshToken() {
        // given
        final ReissueTokenRequest mockReissueTokenRequest = mock(ReissueTokenRequest.class);
        given(refreshTokenRepository.existsByToken(any())).willReturn(false);

        // when then
        assertThatThrownBy(() -> authService.reissueAccessToken(mockReissueTokenRequest))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.AUTH_REFRESH_TOKEN_NOT_EXIST.getMessage());

        then(jwtTokenIssuer).should(times(1)).validateToken(any());
    }

    @Test
    @DisplayName("refresh accessToken 실패 - refreshToken expired")
    void refreshAccessToken_failed_expiredRefreshToken() {
        // given
        final ReissueTokenRequest mockReissueTokenRequest = mock(ReissueTokenRequest.class);
        willThrow(JwtExpiredException.class).given(jwtTokenIssuer).validateToken(any());

        // when then
        assertThatThrownBy(() -> authService.reissueAccessToken(mockReissueTokenRequest))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.AUTH_TOKEN_EXPIRED.getMessage());
    }

    @Test
    @DisplayName("정상적인 refresh token은 access token 재발급 성공한다")
    void reissueAccessToken_success() {
        // given
        final ReissueTokenRequest mockReissueTokenRequest = mock(ReissueTokenRequest.class);

        given(refreshTokenRepository.existsByToken(any())).willReturn(true);
        given(jwtTokenIssuer.createAccessToken(any(), any())).willReturn("accessToken");

        // when
        final TokenInfo.AccessToken accessToken = authService.reissueAccessToken(mockReissueTokenRequest);

        // then
        assertThat(accessToken).isNotNull();
        verify(jwtTokenIssuer, times(1)).validateToken(any());
    }

//    @Test
    @DisplayName("logout 실패 - 존재하지 않는 사용자이면 예외 발생해야 한다.")
    void logoutFailed() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        given(request.getHeader(any())).willReturn("validAccessToken");
        given(jwtTokenIssuer.getUserNameFromJwtToken(any())).willReturn("invalid_user");

        assertThatThrownBy(() -> authService.logout(request))
                .isInstanceOf(CustomException.class)
                .hasMessage("UnAuthorized user");
    }

    @Test
    @DisplayName("정상적인 토큰과 사용자인 경우, logout 성공한다.")
    void logout_success() {
        // given
        try (MockedStatic<SecurityContextHolder> utilities = Mockito.mockStatic(SecurityContextHolder.class)) {
            HttpServletRequest request = mock(HttpServletRequest.class);
            given(request.getHeader(any())).willReturn("validAccessToken");
            given(jwtTokenIssuer.getUserNameFromJwtToken(any())).willReturn("valid_user");

            // when
            authService.logout(request);

            // then
            verify(refreshTokenRepository, times(1)).deleteByEmail(any());
            utilities.verify(SecurityContextHolder::clearContext, times(1));
        }
    }
}