package me.kenux.travelog.web.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.domain.member.dto.response.MemberInfoResponse;
import me.kenux.travelog.domain.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/members")
@RequiredArgsConstructor
@Slf4j
public class MemberManageController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<?> getMembers() {
        final List<MemberInfoResponse> memberInfos = memberService.getMemberInfoResponse();
        return ResponseEntity.ok(memberInfos);
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
