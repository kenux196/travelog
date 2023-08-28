package me.kenux.travelog.web.api;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Hello Swagger")
@RestController
@RequestMapping("/hello")
@Slf4j
public class HelloRestController {

    @ApiOperation(value = "Hello: GET")
    @GetMapping
    public ResponseEntity<?> getHello() {

        log.info("GET: /hello");
        return ResponseEntity.ok("OK~~~");
    }

    @ApiOperation(value = "Hello 이름 등록")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "등록 성공"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    @PostMapping("/{name}")
    public ResponseEntity<?> postHello(@ApiParam(value = "name") @PathVariable String name) {
        log.info("POST: /hello");
        if (name.equals("admin")) {
            throw new IllegalArgumentException("유효하지 않은 이름입니다.");
        }
        return ResponseEntity.ok(name + "이 등록되었습니다.");
    }
}
