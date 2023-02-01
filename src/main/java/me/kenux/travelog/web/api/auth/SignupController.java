package me.kenux.travelog.web.api.auth;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.member.service.dto.request.SignupRequest;
import me.kenux.travelog.domain.member.service.SignupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/signup")
@RequiredArgsConstructor
public class SignupController {

    private final SignupService signupService;

    @PostMapping
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        signupService.signup(request);
        return ResponseEntity.noContent().build();
    }
}
