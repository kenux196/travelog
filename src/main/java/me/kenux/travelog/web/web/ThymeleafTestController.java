package me.kenux.travelog.web.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/thymeleaf")
public class ThymeleafTestController {

    @GetMapping
    public String main() {
        return "thymeleaf/index";
    }

    @GetMapping("/test")
    public String getThymeleafMain(Model model) {
        model.addAttribute("data", "Hello Spring!");
        model.addAttribute("data1", "Hello <b>Spring!<b>");
        return "thymeleaf/main-test";
    }
}
