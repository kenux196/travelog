package me.kenux.travelog.web.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.domain.member.dto.request.LoginRequest;
import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.service.MemberLoginService;
import me.kenux.travelog.global.security.jwt.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class LoginRestController {

    private final MemberLoginService memberLoginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(Authentication authentication) {
        return ResponseEntity.ok(memberLoginService.login(authentication));
    }

    @GetMapping("/test/admin")
    public ResponseEntity<?> login2(Authentication authentication) {
        log.info("GET: /test 요청");
        return ResponseEntity.ok("토큰 사용자: " + authentication.getName());
    }

    @GetMapping("/test/user")
    public ResponseEntity<?> testForUser(Authentication authentication) {
        log.info("GET: /test/user 성공");
        return ResponseEntity.ok("사용자 정보: " + authentication.getName());

    }
}
