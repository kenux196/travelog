package me.kenux.travelog.web.api.member;

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
    public ResponseEntity<?> getMySimpleInfo() {
        final MyInfo.OnlyName myOnlyNameInfo = myInfoService.getMyName();
        return ResponseEntity.ok(myOnlyNameInfo);
    }

}
