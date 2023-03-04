package me.kenux.travelog.global.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.global.exception.JwtTokenExpiredException;
import me.kenux.travelog.global.exception.JwtTokenInvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
class JwtTokenProviderTest {

    private final String secretKey = "12345678901234567890123456789012345678901234567890123456789012345678901234";
    private final int refreshTokenExpirationMinute = 10; // 10 sec
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void beforeEach() {
        // 5 sec
        int tokenExpirationMinute = 5000;
        jwtTokenProvider = new JwtTokenProvider(secretKey, tokenExpirationMinute, refreshTokenExpirationMinute);
    }

    @Test
    void generateAccessToken() {
        final String accessToken = createAccessToken();
        assertThat(accessToken).isNotEmpty();
        log.info("access_token = {}", accessToken);
    }

    private String createAccessToken() {
        return jwtTokenProvider.createAccessToken("admin@test.com", "ADMIN");
    }

    @Test
    void createRefreshToken() {
        final String refreshToken = jwtTokenProvider.createRefreshToken();
        assertThat(refreshToken).isNotEmpty();
        log.info("refresh_token = {}", refreshToken);
    }

    @Test
    void validationAccessToken() {
        final String accessToken = createAccessToken();
        jwtTokenProvider.validateToken(accessToken);
        assertThatCode(() -> jwtTokenProvider.validateToken(accessToken))
                .doesNotThrowAnyException();
    }

    @Test
    void ValidationAccessToken_Failed_Expired() throws InterruptedException {
        final String expiredToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkB0ZXN0LmNvbSIsImF1dGgiOiJBRE1JTiIsImV4cCI6MTY3NzgzMzQzOH0.6Rnn5ZUHajuf7LNntUu2a2nxvbfoQ0sl9kfSEb9QevPxMCVrm_BWiOm2LiHemH6Fvz2BFoY7v3xk3yXra7E68A";
        assertThatThrownBy(() -> jwtTokenProvider.validateToken(expiredToken))
                .isInstanceOf(JwtTokenExpiredException.class);
    }

    @Test
    void ValidationAccessToken_Failed_Invalid() {
        String accessToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkB0ZXN0LmNvbSIsImF1dGgiOiJBRE";
        assertThatThrownBy(() -> jwtTokenProvider.validateToken(accessToken))
                .isInstanceOf(JwtTokenInvalidException.class);
    }
}
