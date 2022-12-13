package me.kenux.travelog.web.web.common;

import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sign-in")
public class SignInController {

    @GetMapping
    public String signInPage(SignInRequest signInRequest) {
        return "view/common/sign-in";
    }
    
    @Data
    static class SignInRequest {
        String username;
        String password;
    }
}
