package me.kenux.travelog.web.rest;

import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.web.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
@Slf4j
public class HelloController {

    @GetMapping
    public ResponseEntity<?> getHello() {
        log.info("GET: /hello");
        return ResponseEntity.ok("OK~~~");
    }

    @PostMapping
    public ResponseEntity<?> postHello(@RequestBody LoginRequest request) {
        log.info("POST: /hello");
        return ResponseEntity.ok(request);
    }
}
