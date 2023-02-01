package me.kenux.travelog.web.admin;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.member.service.dto.request.SignupRequest;
import me.kenux.travelog.domain.member.service.SignupService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/join")
@RequiredArgsConstructor
public class AdminJoinController {

    private final SignupService signupService;

    @GetMapping
    public ModelAndView joinForm() {
        final ModelAndView mav = new ModelAndView("view/common/join-form");
        mav.addObject("joinRequest", new SignupRequest());
        return mav;
    }

    @PostMapping
    public String join(@ModelAttribute SignupRequest request) {
        signupService.signup(request);
        return "redirect:/join/success";
    }

    @GetMapping("/success")
    public String joinSuccess() {
        return "view/common/join-success";
    }
}
