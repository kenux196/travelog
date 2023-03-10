package me.kenux.travelog.global.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.global.exception.JwtExpiredException;
import me.kenux.travelog.global.exception.JwtInvalidException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

@Component
@Slf4j
public class JwtTokenIssuer {

    private final String secretKey;
    private final int tokenExpirationMinute;
    private final int refreshTokenExpirationMinute;
    private final String KEY_ROLES = "roles";

    public JwtTokenIssuer(@Value("${app.jwt.secret}") String secretKey,
                          @Value("${app.jwt.tokenExpiration}") int tokenExpirationMinute,
                          @Value("${app.jwt.refreshTokenExpiration}") int refreshTokenExpirationMinute) {
        this.secretKey = secretKey;
        this.tokenExpirationMinute = tokenExpirationMinute;
        this.refreshTokenExpirationMinute = refreshTokenExpirationMinute;
    }

    public String createAccessToken(String username, String authority) {
        return createToken(username, authority, tokenExpirationMinute);
    }

    public String createRefreshToken(String username, String authority) {
        return createToken(username, authority, refreshTokenExpirationMinute);
    }

    private String createToken(String userName, String authority, int expireMin) {
        Date now = new Date();
        Claims claims = Jwts.claims().setSubject(userName);
        claims.put(KEY_ROLES, Collections.singleton(authority));
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(getExpiration(expireMin))
                .signWith(getSigningKey(secretKey), SignatureAlgorithm.HS512)
                .compact();
    }

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Date getExpiration(int time) {
        Date now = new Date();
        return new Date(now.getTime() + (time));
    }

    public Authentication getAuthentication(String token) {
        final String auth = getAuthorities(token);
        Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(auth.split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();

        final UserDetails principal = new User(getUserNameFromJwtToken(token), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    private Claims getClaims(String token) {
        return jwtParser()
            .parseClaimsJws(token)
            .getBody();
    }

    public void validateToken(String token) {
        try {
            jwtParser().parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw new JwtExpiredException("jwt expired exception", e);
        } catch (Exception e) {
            throw new JwtInvalidException("jwt invalid exception", e);
        }
    }

    private JwtParser jwtParser() {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey(secretKey))
            .build();
    }

    public String getUserNameFromJwtToken(String token) {
        return getClaims(token).getSubject();
    }

    public String getAuthorities(String token) {
        final Claims claims = getClaims(token);
        final List<String> authorities = claims.get(KEY_ROLES, List.class);
        if (authorities.isEmpty() || !authorities.stream().allMatch(auth -> auth.startsWith("ROLE_"))) {
            throw new BadCredentialsException("The token has not roles");
        }
        return String.join(",", authorities);
    }
}
