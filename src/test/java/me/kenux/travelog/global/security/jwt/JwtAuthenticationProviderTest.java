package me.kenux.travelog.global.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import me.kenux.travelog.global.exception.JwtExpiredException;
import me.kenux.travelog.global.exception.JwtInvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JwtAuthenticationProviderTest {

    final int ONE_SECONDS = 1000;
    final int ONE_MINUTE = 60 * ONE_SECONDS;
    final String KEY_ROLES = "roles";
    private final String VALID_SECRET_KEY = "12345678901234567890123456789012345678901234567890123456789012345678901234";
    private final String INVALID_SECRET_KEY = "123456789012345678901234567890123456000000000000000000000000000000000000";

    JwtAuthenticationProvider provider;

    @BeforeEach
    void setup() {
        provider = new JwtAuthenticationProvider(VALID_SECRET_KEY);
    }

    private String createToken(String username, List<String> roles, Date now, int expireMin, String secretKey) {
        final Claims claims = Jwts.claims().setSubject(username);
        claims.put(KEY_ROLES, roles);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + (long) ONE_MINUTE * expireMin))
                .signWith(getSigningKey(secretKey), SignatureAlgorithm.HS512)
                .compact();
    }

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Test
    void givenNotSupportAuthentication_whenCallSupports_thenReturnFalse() {
        assertThat(provider.supports(UsernamePasswordAuthenticationToken.class)).isEqualTo(false);
        assertThat(provider.supports(AbstractAuthenticationToken.class)).isEqualTo(false);
        assertThat(provider.supports(Authentication.class)).isEqualTo(false);
    }

    @Test
    void givenSupportAuthentication_whenCallSupports_thenReturnTrue() {
        assertThat(provider.supports(JwtAuthenticationToken.class)).isEqualTo(true);
    }

    @Test
    void givenTokenMadeByDifferentSecretKey_whenCallAuthentication_thenThrowJwtInvalidException() {

        String invalidToken = createToken("admin", Collections.singletonList("ROLE_ADMIN"), new Date(), 30, INVALID_SECRET_KEY);
        JwtAuthenticationToken authentication = new JwtAuthenticationToken(invalidToken);

        Throwable throwable = assertThrows(JwtInvalidException.class, () -> {
            provider.authenticate(authentication);
        });

        // same with below assert
        // assertThat(throwable, is(instanceOf(JwtInvalidException.class)));
        assertThat(throwable).isInstanceOf(JwtInvalidException.class);
        assertThat(throwable.getMessage()).isEqualTo("jwt invalid exception");
    }

    @Test
    void givenExpiredToken_whenCallAuthentication_thenThrowJwtInvalidException() {

        Date past = new Date(System.currentTimeMillis() - ONE_MINUTE * 10);
        String invalidToken = createToken("admin", Collections.singletonList("ROLE_ADMIN"), past, 5, VALID_SECRET_KEY);
        JwtAuthenticationToken authentication = new JwtAuthenticationToken(invalidToken);

        Throwable throwable = assertThrows(JwtExpiredException.class, () -> {
            provider.authenticate(authentication);
        });

        assertThat(throwable).isInstanceOf(JwtExpiredException.class);
        assertThat(throwable.getMessage()).isEqualTo("jwt expired exception");
    }

    @Test
    public void givenMalformedToken_whenCallAuthentication_thenThrowJwtInvalidException() {

        JwtAuthenticationToken authentication = new JwtAuthenticationToken("some malformed token here");

        Throwable throwable = assertThrows(JwtInvalidException.class, () -> {
            provider.authenticate(authentication);
        });

        assertThat(throwable).isInstanceOf(JwtInvalidException.class);
        assertThat(throwable.getMessage()).isEqualTo("jwt invalid exception");
    }

    @Test
    public void givenNullJwt_whenCallAuthentication_thenThrowJwtInvalidException() {

        JwtAuthenticationToken authentication = new JwtAuthenticationToken(null);

        Throwable throwable = assertThrows(JwtInvalidException.class, () -> {
            provider.authenticate(authentication);
        });

        assertThat(throwable).isInstanceOf(JwtInvalidException.class);
        assertThat(throwable.getMessage()).isEqualTo("jwt invalid exception");
    }

    @Test
    public void givenValidToken_whenCallAuthentication_thenReturnAuthentication() {

        String validToken = createToken("admin", Collections.singletonList("ROLE_ADMIN"), new Date(), 30, VALID_SECRET_KEY);
        JwtAuthenticationToken authentication = new JwtAuthenticationToken(validToken);

        Authentication authenticated = provider.authenticate(authentication);

        assertThat(authenticated.getPrincipal()).isEqualTo("admin");
        assertThat(authenticated.getCredentials()).isEqualTo("");
        Collection<? extends GrantedAuthority> authorities = authenticated.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            assertThat(authority.getAuthority()).isEqualTo("ROLE_ADMIN");
        }
    }
}