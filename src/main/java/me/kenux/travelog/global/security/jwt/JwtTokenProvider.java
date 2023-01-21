package me.kenux.travelog.global.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.global.security.CustomUserDetails;
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
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String secretKey;

    @Value("${app.jwt.tokenExpiration}")
    private int tokenExpirationMinute;

    @Value("${app.jwt.refreshTokenExpiration}")
    private int refreshTokenExpirationMinute;

    public TokenInfo generateJwtToken(Authentication authentication) {
        final String authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        final String accessToken = Jwts.builder()
            .setSubject(authentication.getName())
            .claim("auth", authorities)
            .setExpiration(getExpiration(tokenExpirationMinute))
            .signWith(getSigningKey(secretKey), SignatureAlgorithm.HS512)
            .compact();

        final String refreshToken = Jwts.builder()
            .setExpiration(getExpiration(refreshTokenExpirationMinute))
            .signWith(getSigningKey(secretKey), SignatureAlgorithm.HS512)
            .compact();

        return TokenInfo.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .grantType("Bearer")
            .role(authorities)
            .build();
    }

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Date getExpiration(int time) {
        Date now = new Date();
        return new Date(now.getTime() + (time * 60 * 1000L));
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

    public boolean validateToken(String token) {
        try {
            jwtParser().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error("Failed validateToken : {}", e.getMessage());
        }
        return false;
    }

    private JwtParser jwtParser() {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey(secretKey))
            .build();
    }
}
