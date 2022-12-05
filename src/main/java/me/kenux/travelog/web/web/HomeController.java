package me.kenux.travelog.web.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String home() {
        return "index";
    }

    @GetMapping("/test")
    public String test(Model model) {
        model.addAttribute("data", "Hello Spring!");
        model.addAttribute("data1", "Hello <b>Spring!<b>");
        return "view/test";
    }

}
