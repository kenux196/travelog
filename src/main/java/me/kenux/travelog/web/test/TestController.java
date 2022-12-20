package me.kenux.travelog.web.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {

    @GetMapping
    public String test(Model model) {
        model.addAttribute("data", "Hello Spring!");
        model.addAttribute("data1", "Hello <b>Spring!<b>");
        return "view/test";
    }

    @GetMapping("/cookie")
    public String cookieTest(HttpServletResponse response) {
        log.info("쿠키 생성");
        final Cookie cookie = new Cookie("user-name", "kenux");
        cookie.setDomain("localhost");
        cookie.setPath("/test");
        cookie.setMaxAge(10);
        cookie.setSecure(true);
        response.addCookie(cookie);

        return "redirect:/test";
    }
}
