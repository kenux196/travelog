package me.kenux.travelog.web.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/hello")
@Slf4j
public class HelloRestController {

    @GetMapping
    public ResponseEntity<?> getHello() {
        log.info("GET: /hello");
        return ResponseEntity.ok("OK~~~");
    }

    @PostMapping
    public ResponseEntity<?> postHello() {
        log.info("POST: /hello");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
