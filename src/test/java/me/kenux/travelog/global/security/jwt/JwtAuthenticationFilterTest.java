package me.kenux.travelog.global.security.jwt;

import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;

class JwtAuthenticationFilterTest {

    MockHttpServletRequest mockRequest;
    MockHttpServletResponse mockResponse;
    FilterChain mockFilterChain;
    AuthenticationManager mockAuthenticationManager;
    JwtTokenIssuer mockJwtTokenIssuer;
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setup() {
        mockRequest = new MockHttpServletRequest();
        mockResponse = new MockHttpServletResponse();
        mockFilterChain = Mockito.mock(FilterChain.class);
        mockAuthenticationManager = Mockito.mock(AuthenticationManager.class);
        mockJwtTokenIssuer = Mockito.mock(JwtTokenIssuer.class);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(mockAuthenticationManager);
    }

}