package me.kenux.travelog.web.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.global.exception.CustomException;
import me.kenux.travelog.global.exception.ErrorCode;
import me.kenux.travelog.service.TravelLogService;
import me.kenux.travelog.service.dto.TravelLogDto;
import me.kenux.travelog.service.dto.request.TravelLogSaveRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/my-travel")
@RequiredArgsConstructor
@Slf4j
public class TravelHistoryController {

    private final TravelLogService travelLogService;

    @PostMapping
    public ResponseEntity<?> createHistory(@Valid @RequestBody TravelLogSaveRequest request) {
        travelLogService.registerTravelLog(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getTravelHistoryByUser(@PathVariable String userId) {
        // TODO - this is for test. will remove. 2022-11-02 skyun
        if (userId.equals("hacker")) {
            throw new CustomException("불량 사용자입니다.", ErrorCode.MEMBER_NOT_FOUND);
        }

        return ResponseEntity.ok(userId + "의 여행지 리스트 전달...");
    }

    @GetMapping("/{userId}/{historyNum}")
    public ResponseEntity<?> getTravelHistoryDetail(@PathVariable String userId,
                                                    @PathVariable Long historyNum) {
        // TODO - this is for test. will remove. 2022-11-02 skyun
        if (userId.equals("hacker")) {
            throw new CustomException("불량 사용자입니다.", ErrorCode.MEMBER_NOT_FOUND);
        }

        if (historyNum == 0) {
            throw new CustomException("데이터 없음", ErrorCode.DATA_NOT_FOUND);
        }

        return ResponseEntity.ok(userId + "의 " + historyNum + " 게시물 상세보기");
    }
}
