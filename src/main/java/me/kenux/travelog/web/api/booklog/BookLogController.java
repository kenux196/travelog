package me.kenux.travelog.web.api.booklog;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.booklog.service.BookLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/book-logs")
@RequiredArgsConstructor
public class BookLogController {

    private final BookLogService bookLogService;


    @GetMapping
    public ResponseEntity<?> getBookLogs(Long memberId) {
        return ResponseEntity.ok(bookLogService.getBookLogs(memberId));
    }
}