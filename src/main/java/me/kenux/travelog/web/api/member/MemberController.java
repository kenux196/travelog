package me.kenux.travelog.web.api.member;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.member.service.MemberService;
import me.kenux.travelog.domain.member.service.dto.response.MemberInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Member Controller", description = "회원 관리 API (일반 사용자 전용)")
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{id}")
    @Operation(summary = "회원 정보 조회", description = "회원 번호(id)를 이용하여 회원을 조회한다.")
    @ApiResponse(responseCode = "200", description = "회원 조회 성공",
            content = @Content(schema = @Schema(implementation = MemberInfo.SimpleResponse.class)))
    public ResponseEntity<?> getMyInfo(@PathVariable("id") Long id) {
        final MemberInfo.SimpleResponse memberInfo = memberService.getMemberSimpleInfo(id);
        return ResponseEntity.ok(memberInfo);
    }
}
