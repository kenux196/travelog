package me.kenux.travelog.web.api;

import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.domain.member.dto.request.LoginRequest;
import me.kenux.travelog.domain.member.entity.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@Slf4j
public class LoginRestController {

    @PostMapping
    public ResponseEntity<?> login(Authentication authentication) {
        final Member member = (Member) authentication.getPrincipal();
        log.info("로그인 성공한 경우이다. : username={}, roles={} ", member.getUsername(), member.getAuthorities());
        return ResponseEntity.ok("로그인 성공");
    }

    @GetMapping
    public ResponseEntity<?> login2() {
        log.info("GET: 로그인 요청 받음");
        return ResponseEntity.ok("로그인 성공");
    }
}
