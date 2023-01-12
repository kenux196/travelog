package me.kenux.travelog.global.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

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
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact();

        final String refreshToken = Jwts.builder()
            .setExpiration(getExpiration(refreshTokenExpirationMinute))
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact();

        return TokenInfo.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .grantType("Bearer")
            .build();
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

        Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        final UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT Signature");
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT Token");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT Token");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT Token");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty");
        }
        return false;
    }
}
