package me.kenux.travelog.web.api;

import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.service.dto.request.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@Slf4j
public class LoginRestController {

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        log.info("POST: 로그인 요청 받음 => {}", request);
        return ResponseEntity.ok("로그인 성공");
    }

    @GetMapping
    public ResponseEntity<?> login2() {
        log.info("GET: 로그인 요청 받음");
        return ResponseEntity.ok("로그인 성공");
    }
}
