package me.kenux.travelog.web.api.book;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.domain.booklog.repository.dto.BookSearchCond;
import me.kenux.travelog.domain.booklog.service.BookManagementService;
import me.kenux.travelog.domain.booklog.service.BookSearchService;
import me.kenux.travelog.domain.booklog.service.dto.AddBookRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This is tested for data
 */

@Tag(name = "Book Controller", description = "책 API")
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
    public ResponseEntity<?> registerBooks(@RequestBody AddBookRequest request) {
        bookManagementService.addBook(request);
        return ResponseEntity.noContent().build();
    }
}
