package me.kenux.travelog.web.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.domain.member.dto.request.LoginRequest;
import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.service.MemberLoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class LoginRestController {

    private final MemberLoginService memberLoginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(memberLoginService.login(request));
    }

    @GetMapping("/test/admin")
    public ResponseEntity<?> testAdmin(Authentication authentication) {
        log.info("GET: /test 요청");

//        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        Member member = authentication == null ? null : (Member) auth.getPrincipal();
//        log.info("member={}", member);


        return ResponseEntity.ok("토큰 사용자: " + authentication.getName());
    }

    @GetMapping("/test/admin2")
    public ResponseEntity<?> testAdmin2() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = authentication == null ? null : (Member) authentication.getPrincipal();
        return ResponseEntity.ok(member);
    }

    @GetMapping("/test/admin3")
    public ResponseEntity<?> testAdmin3(@AuthenticationPrincipal Member member) {
//        log.info("member={}", member);
//        return ResponseEntity.ok(member);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/test/user")
    public ResponseEntity<?> testForUser(Authentication authentication) {
        log.info("GET: /test/user 성공");
        return ResponseEntity.ok("사용자 정보: " + authentication.getName());

    }
}
