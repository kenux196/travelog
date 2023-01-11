package me.kenux.travelog.web.api;

import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.domain.member.dto.request.LoginRequest;
import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.global.security.jwt.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class LoginRestController {

    @PostMapping("/login")
    public ResponseEntity<?> login(Authentication authentication) {
        final Member member = (Member) authentication.getPrincipal();
        log.info("로그인 성공한 경우이다. : username={}, roles={} ", member.getUsername(), member.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String jwtToken = JwtTokenProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(jwtToken);
    }

    @GetMapping("/test")
    public ResponseEntity<?> login2(Authentication authentication) {
        log.info("GET: /test 요청");
        final Member member = (Member) authentication.getPrincipal();
        return ResponseEntity.ok("토큰 사용자: " + member.getUsername());
    }
}
