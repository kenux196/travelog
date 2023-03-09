package me.kenux.travelog.global.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.global.exception.JwtExpiredException;
import me.kenux.travelog.global.exception.JwtInvalidException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    public final String KEY_ROLES = "roles";
    private final String secretKey;
    private final int tokenExpirationMinute;
    private final int refreshTokenExpirationMinute;

    public JwtAuthenticationProvider(@Value("${app.jwt.secret}") String secretKey,
                                     @Value("${app.jwt.tokenExpiration}") int tokenExpirationMinute,
                                     @Value("${app.jwt.refreshTokenExpiration}") int refreshTokenExpirationMinute) {
        this.secretKey = secretKey;
        this.tokenExpirationMinute = tokenExpirationMinute;
        this.refreshTokenExpirationMinute = refreshTokenExpirationMinute;
    }

    private Collection<? extends GrantedAuthority> createGrantedAuthorities(Claims claims) {
        List<String> roles = (List<String>) claims.get(KEY_ROLES);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String role : roles) {
            grantedAuthorities.add(() -> role);
        }
        return grantedAuthorities;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Claims claims;
        try {
            claims = jwtParser()
                    .parseClaimsJws(((JwtAuthenticationToken) authentication).getJsonWebToken())
                    .getBody();
        } catch (ExpiredJwtException expiredJwtException) {
            throw new JwtExpiredException(expiredJwtException);
        } catch (SignatureException | MalformedJwtException | IllegalArgumentException signatureException) {
            throw new JwtInvalidException(signatureException);
        }
        return new JwtAuthenticationToken(claims.getSubject(), "", createGrantedAuthorities(claims));
    }

    private JwtParser jwtParser() {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(secretKey))
                .build();
    }

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}