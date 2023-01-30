package me.kenux.travelog.global.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class JwtTokenProviderTest {

    private String secretKey = "12345678901234567890123456789012345678901234567890123456789012345678901234";
    private int tokenExpirationMinute = 5000;  // 5 sec
    private int refreshTokenExpirationMinute = 10; // 10 sec

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void beforeEach() {
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
    void validationAccessKey() {
        final String accessToken = createAccessToken();
        final JwtValidationResult result = jwtTokenProvider.validateToken(accessToken);
        assertThat(result).isEqualTo(JwtValidationResult.VALID);
    }

    @Test
    void ValidationAccessKey_Failed_Expired() throws InterruptedException {
        jwtTokenProvider = new JwtTokenProvider(secretKey, 1000 , refreshTokenExpirationMinute);
        final String accessToken = jwtTokenProvider.createAccessToken("admin@test.com", "ADMIN");
        Thread.sleep(2000);

        final JwtValidationResult result = jwtTokenProvider.validateToken(accessToken);
        assertThat(result).isEqualTo(JwtValidationResult.EXPIRED);
    }

    @Test
    void ValidationAccessKey_Failed_Invalid() {
        String accessToken = createAccessToken();
        accessToken = accessToken.toUpperCase();
        final JwtValidationResult result = jwtTokenProvider.validateToken(accessToken);
        assertThat(result).isEqualTo(JwtValidationResult.INVALID);
    }
}
