package me.kenux.travelog.global.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import me.kenux.travelog.global.exception.JwtInvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    MockHttpServletRequest mockRequest;
    MockHttpServletResponse mockResponse;
    FilterChain mockFilterChain;
    AuthenticationManager mockAuthenticationManager;
    JwtTokenIssuer mockJwtTokenIssuer;
    JwtAuthenticationFilter filter;

    @BeforeEach
    void setup() {
        mockRequest = new MockHttpServletRequest();
        mockResponse = new MockHttpServletResponse();
        mockFilterChain = Mockito.mock(FilterChain.class);
        mockAuthenticationManager = Mockito.mock(AuthenticationManager.class);
        mockJwtTokenIssuer = Mockito.mock(JwtTokenIssuer.class);
        filter = new JwtAuthenticationFilter(mockAuthenticationManager);
    }

    @Test
    void givenTokenNotInHeader_whenDoFilterInternal_thenAuthenticationManagerNotBeanCalled()
            throws ServletException, IOException {

        // given
        given(mockAuthenticationManager.authenticate(any())).willReturn(null);

        // when
        filter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        // then
        then(mockAuthenticationManager).should(never()).authenticate(any());
        then(mockFilterChain).should(times(1)).doFilter(mockRequest, mockResponse);
    }

    @Test
    void givenInvalidTokenInHeader_whenDoFilterInternal_thenAuthenticationManagerNotBeanCalled()
            throws ServletException, IOException {
        // setup
        mockRequest.addHeader("Authorization", "invalid token");
        when(mockAuthenticationManager.authenticate(any())).thenReturn(null);

        // action
        filter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        // verify
        verify(mockAuthenticationManager, never()).authenticate(any());
        verify(mockFilterChain, times(1)).doFilter(mockRequest, mockResponse);
    }

    @Test
    @DisplayName("정상토큰이 인증에 실패하면, SecurityContext에서 가져오는 authenticationd은 null이다.")
    void givenReturnNullAfterAuthenticateWithValidToken_whenDoFilterInternal_thenAuthenticationFromSecurityContextHolderIsNull()
            throws ServletException, IOException {
        // setup
        mockRequest.addHeader("Authorization", "Bearer valid_token");
        JwtAuthenticationToken token = new JwtAuthenticationToken("valid_token");
        when(mockAuthenticationManager.authenticate(token)).thenReturn(null);

        // action
        filter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        // verify
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    void givenThrowAuthenticationException_whenDoFilterInternal_thenSecurityContextInContextHolderIsNullAndClearContextBeenCalled()
            throws ServletException, IOException {
        // setup
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        try (MockedStatic<SecurityContextHolder> utilities = Mockito.mockStatic(SecurityContextHolder.class)) {
            utilities.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            mockRequest.addHeader("Authorization", "Bearer valid_token");
            JwtAuthenticationToken token = new JwtAuthenticationToken("valid_token");

            when(mockAuthenticationManager.authenticate(token)).thenThrow(new JwtInvalidException("jwt invalid exception"));

            // action
            filter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

            // verify
            utilities.verify(SecurityContextHolder::clearContext, times(1));
            assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        }
        Mockito.clearAllCaches();
    }

    @Test
    @DisplayName("정상토큰으로 정상적인 인증이 되면, SecurityContextHodler에서 인증정보가 담긴다.")
    void givenValidToken_whenDoFilterInternal_thenSecurityContextHasAuthentication() throws ServletException, IOException {
        // given
        mockRequest.addHeader("Authorization", "Bearer valid_token");
        JwtAuthenticationToken token = new JwtAuthenticationToken("valid_token");
        JwtAuthenticationToken authenticatedToken = new JwtAuthenticationToken(
                "user1",
                "",
                Collections.singleton(() -> "ROLE_USER"));

        given(mockAuthenticationManager.authenticate(token)).willReturn(authenticatedToken);

        // when
        filter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        // then
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isEqualTo(authenticatedToken);
    }
}