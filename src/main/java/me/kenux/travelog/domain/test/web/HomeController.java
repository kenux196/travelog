package me.kenux.travelog.domain.test.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@Slf4j
public class HomeController {

    @GetMapping
    public String home() {
        log.info("Home!!!!!!!!!!!");
        return "index2";
    }
}
