package me.kenux.travelog.web.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.domain.travellog.service.TraveLogService;
import me.kenux.travelog.domain.travellog.service.dto.request.TravelLogSaveRequest;
import me.kenux.travelog.global.exception.CustomException;
import me.kenux.travelog.global.exception.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "TravelHistory Controller", description = "여행일지 API")
@RestController
@RequestMapping("/api/travels")
@RequiredArgsConstructor
@Slf4j
public class TravelHistoryRestController {

    private final TraveLogService traveLogService;

    @PostMapping
    public ResponseEntity<?> createHistory(@Valid @RequestBody TravelLogSaveRequest request) {
        traveLogService.saveTravelLog(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getTravelHistoryByUser(@PathVariable String userId) {
        // TODO - this is for test. will remove. 2022-11-02 skyun
        if (userId.equals("hacker")) {
            throw new CustomException(ErrorCode.MEMBER_NOT_EXIST);
        }

        return ResponseEntity.ok(userId + "의 여행지 리스트 전달...");
    }

    @GetMapping("/{userId}/{historyNum}")
    public ResponseEntity<?> getTravelHistoryDetail(@PathVariable String userId,
                                                    @PathVariable Long historyNum) {
        // TODO - this is for test. will remove. 2022-11-02 skyun
        if (userId.equals("hacker")) {
            throw new CustomException(ErrorCode.MEMBER_NOT_EXIST);
        }

        if (historyNum == 0) {
            throw new CustomException(ErrorCode.DATA_NOT_FOUND);
        }

        return ResponseEntity.ok(userId + "의 " + historyNum + " 게시물 상세보기");
    }
}
