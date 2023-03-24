package me.kenux.travelog.web.api.booklog;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.booklog.service.BookLogService;
import me.kenux.travelog.domain.booklog.service.dto.AddBookLogRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book-logs")
@RequiredArgsConstructor
public class BookLogController {

    private final BookLogService bookLogService;


    @GetMapping
    public ResponseEntity<?> getBookLogs(Long memberId) {
        return ResponseEntity.ok(bookLogService.getBookLogs(memberId));
    }

    @PostMapping
    public ResponseEntity<?> addBookShelf(@RequestBody AddBookLogRequest request) {
        return ResponseEntity.ok(bookLogService.addNewBookLog(request));
    }
}