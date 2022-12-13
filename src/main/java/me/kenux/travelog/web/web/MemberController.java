package me.kenux.travelog.web.web;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public String getMembers(Model model) {
        model.addAttribute("memberInfo", memberService.getMemberInfoResponse());
        return "view/admin/member-list";
    }

    @GetMapping("/{id}")
    public String getMemberDetail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("memberInfo", memberService.getMemberDetail(id));
        return "view/member/detail";
    }
}
