package me.kenux.travelog.web.api.book;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.booklog.service.BookLogService;
import me.kenux.travelog.domain.booklog.service.dto.AddBookLogRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "BookLog Controller", description = "북로그 API")
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
