package me.kenux.travelog.web.api.auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.member.service.SignupService;
import me.kenux.travelog.domain.member.service.dto.request.SignupRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Signup Controller", description = "회원 가입 API")
@RestController
@RequestMapping("/api/signup")
@RequiredArgsConstructor
public class SignupController {

    private final SignupService signupService;

    @PostMapping
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest request) {
        signupService.signup(request);
        return ResponseEntity.noContent().build();
    }
}
