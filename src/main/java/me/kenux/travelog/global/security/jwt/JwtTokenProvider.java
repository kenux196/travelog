package me.kenux.travelog.global.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.global.exception.CustomException;
import me.kenux.travelog.global.exception.ErrorCode;
import me.kenux.travelog.global.exception.JwtTokenExpiredException;
import me.kenux.travelog.global.exception.JwtTokenInvalidException;
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
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {

    private final String secretKey;
    private final int tokenExpirationMinute;
    private final int refreshTokenExpirationMinute;

    public JwtTokenProvider(@Value("${app.jwt.secret}") String secretKey,
                            @Value("${app.jwt.tokenExpiration}") int tokenExpirationMinute,
                            @Value("${app.jwt.refreshTokenExpiration}") int refreshTokenExpirationMinute) {
        this.secretKey = secretKey;
        this.tokenExpirationMinute = tokenExpirationMinute;
        this.refreshTokenExpirationMinute = refreshTokenExpirationMinute;
    }

    public String createAccessToken(String username, String authorities) {
        return Jwts.builder()
            .setSubject(username)
            .claim("auth", authorities)
            .setExpiration(getExpiration(tokenExpirationMinute))
            .signWith(getSigningKey(secretKey), SignatureAlgorithm.HS512)
            .compact();
    }

    public String createRefreshToken() {
        return Jwts.builder()
            .setExpiration(getExpiration(refreshTokenExpirationMinute))
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
        final Claims claims = getClaims(token);

        if (claims.get("auth") == null) {
            throw new BadCredentialsException("The token has not roles");
        }

        final String auth = claims.get("auth").toString();
        Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(auth.split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();

        final UserDetails principal = new User(claims.getSubject(), "", authorities);
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
            throw new JwtTokenExpiredException(e);
        } catch (Exception e) {
            throw new JwtTokenInvalidException(e);
        }
    }

    public boolean validateToken2(String token) {
        jwtParser().parseClaimsJws(token);
        return true;
    }

    private JwtParser jwtParser() {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey(secretKey))
            .build();
    }

    public String getUserNameFromJwtToken(String token) {
        return getClaims(token).getSubject();
    }
}
