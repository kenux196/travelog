package me.kenux.travelog.web.api.member;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.member.dto.request.MemberJoinRequest;
import me.kenux.travelog.domain.member.service.MemberJoinService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/join")
@RequiredArgsConstructor
public class MemberJoinController {

    private final MemberJoinService memberJoinService;

    @PostMapping
    public ResponseEntity<?> join(@RequestBody MemberJoinRequest request) {
        memberJoinService.join(request);
        return ResponseEntity.noContent().build();
    }
}
