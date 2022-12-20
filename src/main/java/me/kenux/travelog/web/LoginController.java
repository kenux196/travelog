package me.kenux.travelog.web;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.service.MemberLoginService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final MemberLoginService memberLoginService;

    @GetMapping
    public String loginForm(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "view/common/login-form";
    }

    @GetMapping("/success")
    public String login(Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();
        log.info("{}, 로그인 성공", member.getEmail());
        memberLoginService.loginSuccessProcess(member.getId());
        return "redirect:/";
    }

    @Data
    public static class LoginRequest {
        String username;
        String password;
    }
}
