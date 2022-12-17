package me.kenux.travelog.domain.member.web;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.member.service.MemberJoinService;
import me.kenux.travelog.domain.member.dto.request.MemberJoinRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/join")
@RequiredArgsConstructor
public class JoinController {

    private final MemberJoinService memberJoinService;

    @GetMapping
    public ModelAndView joinForm() {
        final ModelAndView mav = new ModelAndView("view/common/join-form");
        mav.addObject("joinRequest", new MemberJoinRequest());
        return mav;
    }

    @PostMapping
    public String join(@ModelAttribute MemberJoinRequest request) {
        memberJoinService.join(request);
        return "redirect:/join/success";
    }

    @GetMapping("/success")
    public String joinSuccess() {
        return "view/common/join-success";
    }
}
