package me.kenux.travelog.web.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.domain.member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        return "view/member/member-list";
    }

    @GetMapping("/delete")
    public String deleteMember(@RequestParam("id") Long id) {
        log.info("will delete member id = {}", id);
        memberService.removeMember(id);
        return "redirect:/admin/members";
    }

    @GetMapping("/{id}/block")
    public String blockMember(@PathVariable("id") Long id) {
        log.info("member {} will blocked.", id);
        memberService.blockMember(id);
        return "redirect:/admin/members";
    }
}
