package me.kenux.travelog.web.api.member;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.member.service.MemberService;
import me.kenux.travelog.domain.member.service.MyInfoService;
import me.kenux.travelog.domain.member.service.dto.response.MyInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "MyInfo Controller", description = "회원 정보 API")
@RestController
@RequestMapping("/api/members/me")
@RequiredArgsConstructor
public class MyInfoController {

    private final MyInfoService myInfoService;

    @GetMapping
    @Operation(summary = "회원 정보를 가져온다.", description = "로그인한 회원의 정보(이름)을 가져온다.")
    @ApiResponse(responseCode = "200", description = "요청 성공",
            content = @Content(schema = @Schema(implementation = MyInfo.OnlyName.class)))
    @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
    public ResponseEntity<?> getMySimpleInfo() {
        final MyInfo.OnlyName myOnlyNameInfo = myInfoService.getMyName();
        return ResponseEntity.ok(myOnlyNameInfo);
    }

}
