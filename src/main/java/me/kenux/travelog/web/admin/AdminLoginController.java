package me.kenux.travelog.web.admin;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.service.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/login")
@RequiredArgsConstructor
@Slf4j
public class AdminLoginController {

    private final AuthService authService;

    @GetMapping
    public String loginForm(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "view/common/login-form";
    }

    @GetMapping("/success")
    public String login(Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();
        log.info("{}, 로그인 성공", member.getEmail());
        authService.loginSuccessProcess(member.getId());
        return "redirect:/admin";
    }

    @Data
    public static class LoginRequest {
        String username;
        String password;
    }
}
