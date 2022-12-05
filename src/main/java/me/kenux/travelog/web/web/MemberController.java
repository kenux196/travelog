package me.kenux.travelog.web.web;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.service.MemberService;
import me.kenux.travelog.service.dto.response.MemberInfoResponse;
import me.kenux.travelog.service.dto.request.MemberJoinRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public String getMembers(Model model) {
        model.addAttribute("memberInfo", memberService.getMemberInfoResponse());
        return "view/member/list";
    }

    @GetMapping("/join")
    public ModelAndView getMemberJoinForm() {
        final ModelAndView mav = new ModelAndView("view/member/join");
        mav.addObject("joinRequest", new MemberJoinRequest());
        return mav;
    }

    @PostMapping("/join")
    public String joinMember(@ModelAttribute MemberJoinRequest request) {
        memberService.joinMember(request);
        return "redirect:/";
    }
}
