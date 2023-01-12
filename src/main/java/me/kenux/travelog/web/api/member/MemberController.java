package me.kenux.travelog.web.api.member;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.member.dto.response.MemberInfoResponse;
import me.kenux.travelog.domain.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getMemberDetail(@PathVariable("id") Long id) {
        final MemberInfoResponse memberDetail = memberService.getMemberDetail(id);
        return ResponseEntity.ok(memberDetail);
    }
}
