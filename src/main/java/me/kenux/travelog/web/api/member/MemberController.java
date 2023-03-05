package me.kenux.travelog.web.api.member;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.member.service.MemberService;
import me.kenux.travelog.domain.member.service.dto.response.MemberInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getMyInfo(@PathVariable("id") Long id) {
        final MemberInfo.SimpleResponse memberInfo = memberService.getMemberSimpleInfo(id);
        return ResponseEntity.ok(memberInfo);
    }
}
