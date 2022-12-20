package me.kenux.travelog.web;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{id}")
    public String getMemberDetail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("memberInfo", memberService.getMemberDetail(id));
        return "view/member/detail";
    }
}
