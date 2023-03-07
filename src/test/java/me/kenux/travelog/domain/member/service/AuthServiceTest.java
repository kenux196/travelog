package me.kenux.travelog.domain.member.service;

import me.kenux.travelog.domain.member.service.dto.TokenInfo;
import me.kenux.travelog.domain.member.service.dto.UserDetailsImpl;
import me.kenux.travelog.domain.member.service.dto.request.LoginRequest;
import me.kenux.travelog.global.exception.CustomException;
import me.kenux.travelog.global.exception.ErrorCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserDetailsService userDetailsService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManagerBuilder authenticationManagerBuilder;
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

//    @Test
//    @DisplayName("로그인 성공")
//    void login_success() throws Exception {
//        // given
//        ObjectPostProcessor<Object> opp = mock(ObjectPostProcessor.class);
//        AuthenticationProvider provider = mock(AuthenticationProvider.class);
//        authenticationManagerBuilder = new AuthenticationManagerBuilder(opp);
//        authenticationManagerBuilder.authenticationProvider(provider);
//        authenticationManagerBuilder.build();
//
//        LoginRequest loginRequest = getLoginRequest();
//        final UserDetails userDetails = getUserDetails();
//        given(userDetailsService.loadUserByUsername(anyString())).willReturn(userDetails);
//        given(passwordEncoder.matches(any(CharSequence.class), anyString())).willReturn(true);
////        given(authenticationManagerBuilder.getObject()).willReturn(authenticationManagerBuilder.getObject());
//        given(authenticationManagerBuilder.getObject().authenticate(any())).willReturn(any(Authentication.class));
//
//        // when
//        final TokenInfo.Full tokenInfo = authService.login(loginRequest);
//
//        // then
//        assertThat(tokenInfo).isNotNull();
//
//    }

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
}