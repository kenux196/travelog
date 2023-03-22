package me.kenux.travelog.global.security.jwt;

import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.global.exception.JwtExpiredException;
import me.kenux.travelog.global.exception.JwtInvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.*;

@Slf4j
class JwtTokenIssuerTest {

    private final String username = "admin@test.com";
    private final String authorities = "ROLE_ADMIN";
    private final String secretKey = "12345678901234567890123456789012345678901234567890123456789012345678901234";
    private final int refreshTokenExpirationMinute = 10; // 10 sec
    private JwtTokenIssuer jwtTokenIssuer;

    @BeforeEach
    void beforeEach() {
        // 5 sec
        int tokenExpirationMinute = 5000;
        jwtTokenIssuer = new JwtTokenIssuer(secretKey, tokenExpirationMinute, refreshTokenExpirationMinute);
    }

    @Test
    void generateAccessToken() {
        final String accessToken = createAccessToken();
        assertThat(accessToken).isNotEmpty();
        log.info("access_token = {}", accessToken);
    }

    private String createAccessToken() {
        return jwtTokenIssuer.createAccessToken(username, authorities);
    }

    @Test
    void createRefreshToken() {
        final String refreshToken = jwtTokenIssuer.createRefreshToken(username, authorities);
        assertThat(refreshToken).isNotEmpty();
        log.info("refresh_token = {}", refreshToken);
    }

    @Test
    void validationAccessToken() {
        final String accessToken = createAccessToken();
        jwtTokenIssuer.validateToken(accessToken);
        assertThatCode(() -> jwtTokenIssuer.validateToken(accessToken))
                .doesNotThrowAnyException();
    }

    @Test
    void ValidationAccessToken_Failed_Expired() throws InterruptedException {
        final String expiredToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkB0ZXN0LmNvbSIsImF1dGgiOiJBRE1JTiIsImV4cCI6MTY3NzgzMzQzOH0.6Rnn5ZUHajuf7LNntUu2a2nxvbfoQ0sl9kfSEb9QevPxMCVrm_BWiOm2LiHemH6Fvz2BFoY7v3xk3yXra7E68A";
        assertThatThrownBy(() -> jwtTokenIssuer.validateToken(expiredToken))
                .isInstanceOf(JwtExpiredException.class);
    }

    @Test
    void ValidationAccessToken_Failed_Invalid() {
        String accessToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkB0ZXN0LmNvbSIsImF1dGgiOiJBRE";
        assertThatThrownBy(() -> jwtTokenIssuer.validateToken(accessToken))
                .isInstanceOf(JwtInvalidException.class);
    }

    @Test
    @DisplayName("JWT 토큰에서 username 가져온다.")
    void getUserNameFromJwtToken() {
        final String accessToken = createAccessToken();

        final String result = jwtTokenIssuer.getUserNameFromJwtToken(accessToken);

        assertThat(result).isEqualTo(username);
    }

    @Test
    @DisplayName("JWT 토큰에서 사용자의 Authetication 정보를 가져온다.")
    void getAuthentication() {
        final String accessToken = createAccessToken();

        final Authentication authentication = jwtTokenIssuer.getAuthentication(accessToken);

        assertThat(authentication).isInstanceOf(UsernamePasswordAuthenticationToken.class);
    }

    @Test
    @DisplayName("JWT 토큰에 authority 정보가 없으면 예외 발생")
    void hasNotRole_exception() {
        final String accessToken = jwtTokenIssuer.createAccessToken(username, "");

        assertThatThrownBy(() -> jwtTokenIssuer.getAuthentication(accessToken))
                .isInstanceOf(BadCredentialsException.class);
    }
}
