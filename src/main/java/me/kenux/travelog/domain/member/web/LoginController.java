package me.kenux.travelog.domain.member.web;

import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String loginForm(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "view/common/login-form";
    }

    @Data
    public static class LoginRequest {
        String username;
        String password;
    }
}
