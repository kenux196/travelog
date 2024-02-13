package me.kenux.travelog.web.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Hello Controller", description = "테스트 컨트롤러 모음")
@RestController
@RequestMapping("/hello")
@Slf4j
public class HelloRestController {

    @Operation(summary = "Hello: GET")
    @GetMapping
    public ResponseEntity<?> getHello() {

        log.info("GET: /hello");
        return ResponseEntity.ok("OK~~~");
    }

    @Operation(summary = "Hello 이름 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @PostMapping("/{name}")
    public ResponseEntity<?> postHello(@PathVariable String name) {
        log.info("POST: /hello");
        if (name.equals("admin")) {
            throw new IllegalArgumentException("유효하지 않은 이름입니다.");
        }
        return ResponseEntity.ok(name + "이 등록되었습니다.");
    }
}
