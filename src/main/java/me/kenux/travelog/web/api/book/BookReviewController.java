package me.kenux.travelog.web.api.book;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.booklog.service.BookReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/book-review")
@RequiredArgsConstructor
public class BookReviewController {

    private final BookReviewService bookReviewService;

    @GetMapping
    public ResponseEntity<?> getReviews(Long bookId) {
        return ResponseEntity.ok(bookReviewService.getBookReviewWithMember(bookId));
    }
}
