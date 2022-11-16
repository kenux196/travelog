package me.kenux.travelog.web.web;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.service.MemberService;
import me.kenux.travelog.web.dto.MemberInfoResponse;
import me.kenux.travelog.web.dto.MemberJoinRequest;
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
        model.addAttribute("memberInfo", getTestData());
        return "/view/member/list";
    }

    @GetMapping("/join")
    public ModelAndView getMemberJoinForm() {
        final ModelAndView mav = new ModelAndView("/view/member/join");
        mav.addObject("joinRequest", new MemberJoinRequest());
        return mav;
    }

    @PostMapping("/join")
    public String joinMember(@ModelAttribute MemberJoinRequest request) {
        memberService.joinMember(request.toEntity(), request.getPassword());
        return "redirect:/";
    }

    private List<MemberInfoResponse> getTestData() {
        List<MemberInfoResponse> memberInfoResponses = new ArrayList<>();
        for (int i = 0; i < 23; i++) {
            final long id = (long) i + 1;
            final String name = "member_" + id;
            final String email = name + "@email.com";
            final LocalDateTime joinDateBase = LocalDateTime.of(2022, 01, 01, 10, 10, 10);
            final MemberInfoResponse memberInfoResponse = new MemberInfoResponse();
            memberInfoResponse.setId(id);
            memberInfoResponse.setName(name);
            memberInfoResponse.setEmail(email);
            memberInfoResponse.setJoinDate(joinDateBase.plusDays(i));
            memberInfoResponse.setStatus("normal");
            memberInfoResponses.add(memberInfoResponse);
        }
        return memberInfoResponses;
    }
}
