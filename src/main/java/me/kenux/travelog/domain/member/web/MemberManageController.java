package me.kenux.travelog.domain.member.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.domain.member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/members")
@RequiredArgsConstructor
@Slf4j
public class MemberManageController {

    private final MemberService memberService;

    @GetMapping
    public String getMembers(Model model) {
        model.addAttribute("memberInfo", memberService.getMemberInfoResponse());
        return "view/admin/member-list";
    }

    @GetMapping("/delete")
    public String deleteMember(@RequestParam("id") Long id) {
        log.info("will delete member id = {}", id);
        return "redirect:/admin/members";
    }
}
