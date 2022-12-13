package me.kenux.travelog.web.web;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;

    @GetMapping("/members")
    public String getMembers(Model model) {
        model.addAttribute("memberInfo", memberService.getMemberInfoResponse());
        return "view/admin/member-list";
    }
}
