package me.kenux.travelog.web.api.book;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.domain.book.repository.dto.BookSearchCond;
import me.kenux.travelog.domain.book.service.BookManagementService;
import me.kenux.travelog.domain.book.service.BookSearchService;
import me.kenux.travelog.domain.book.service.dto.BookInfoDto;
import me.kenux.travelog.domain.book.service.dto.RegisterBookRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This is tested for data
 */

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookManagementService bookManagementService;
    private final BookSearchService bookSearchService;

    @GetMapping
    public ResponseEntity<?> getBooks(BookSearchCond cond) {
        return ResponseEntity.ok(bookSearchService.getBooks(cond));
    }

    @PostMapping
    public ResponseEntity<?> registerBooks(@RequestBody RegisterBookRequest request) {
        // 책 등록 처리
        log.info(request.toString());
        bookManagementService.addBook(request);
        return ResponseEntity.noContent().build();
    }
}
