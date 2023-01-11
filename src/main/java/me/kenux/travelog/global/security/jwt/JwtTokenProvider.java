package me.kenux.travelog.global.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.domain.member.entity.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
public class JwtTokenProvider {

//    @Value("${app.jwtSecret default kenux}")
    private static final String JWT_SECRET = "kenux";

//    @Value("${app.jwtExpirationMs:5000}")
    private static final int JWT_EXPIRATION_MS = (int) (1000L * 60 * 5); // 5분
    private static final int JWT_REFRESH_EXPIRATION_MS = (int) (1000L * 60 * 60); // 1시간

    public static TokenInfo generateJwtToken(Authentication authentication) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_MS);

        final Member principal = (Member) authentication.getPrincipal();
        final String authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        final String accessToken = Jwts.builder()
            .setSubject(principal.getUsername())
            .claim("auth", authorities)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
            .compact();

        final String refreshToken = Jwts.builder()
            .setExpiration(new Date(now.getTime() + JWT_REFRESH_EXPIRATION_MS))
            .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
            .compact();

        return TokenInfo.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .grantType("Bearer")
            .build();
    }

    public static String getUserNameFromJWT(String token) {
        final Claims claims = Jwts.parser()
            .setSigningKey(JWT_SECRET)
            .parseClaimsJws(token)
            .getBody();
        return claims.getSubject();
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
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
