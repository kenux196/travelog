package me.kenux.travelog.web.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @GetMapping
    public String home() {
        log.info("Admin Home!!!!!!!!!!!");
        return "view/admin/admin-home";
    }
}
