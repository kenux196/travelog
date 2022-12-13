package me.kenux.travelog.web.web;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.service.MemberService;
import me.kenux.travelog.service.dto.request.MemberJoinRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignUpController {

    private final MemberService memberService;

    @GetMapping
    public ModelAndView getMemberJoinForm() {
        final ModelAndView mav = new ModelAndView("view/common/signup");
        mav.addObject("joinRequest", new MemberJoinRequest());
        return mav;
    }

    @PostMapping
    public String signup(@ModelAttribute MemberJoinRequest request) {
        memberService.signup(request);
        return "redirect:/signup/ok";
    }

    @GetMapping("/ok")
    public String signupCompleted() {
        return "view/common/signup-completed";
    }
}
