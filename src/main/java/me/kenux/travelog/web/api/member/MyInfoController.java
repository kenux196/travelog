package me.kenux.travelog.web.api.member;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.member.service.MemberService;
import me.kenux.travelog.domain.member.service.dto.response.MyInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members/me")
@RequiredArgsConstructor
public class MyInfoController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<?> getMySimpleInfo(@RequestParam Long id) {
        final MyInfo.Simple mySimpleInfo = memberService.getMySimpleInfo(id);
        return ResponseEntity.ok(mySimpleInfo);
    }

}
