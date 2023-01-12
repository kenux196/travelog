package me.kenux.travelog.web.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.domain.member.dto.response.MemberInfoResponse;
import me.kenux.travelog.domain.member.repository.dto.MemberSearchCond;
import me.kenux.travelog.domain.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/member")
@RequiredArgsConstructor
@Slf4j
public class MemberManageController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<?> getMembers(MemberSearchCond cond) {
        final List<MemberInfoResponse> memberInfos = memberService.getMembers(cond);
        return ResponseEntity.ok(memberInfos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable("id") Long id) {
        log.info("will delete member id = {}", id);
        memberService.removeMember(id);
        return ResponseEntity.ok(id + " 회원을 삭제하였습니다.");
    }

    @PatchMapping("/{id}/block")
    public ResponseEntity<?> blockMember(@PathVariable("id") Long id) {
        log.info("member {} will blocked.", id);
        memberService.blockMember(id);
        return ResponseEntity.ok(id + " 회원을 블랙으로 변경했습니다.");
    }
}
