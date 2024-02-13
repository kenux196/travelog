package me.kenux.travelog.web.api.book;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.booklog.service.BookReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "BookReview Controller", description = "책 리뷰 API")
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
