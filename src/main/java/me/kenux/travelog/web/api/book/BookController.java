package me.kenux.travelog.web.api.book;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class BookController {

    @GetMapping
    public ResponseEntity<?> getBooks() {
        return ResponseEntity.ok(getBookInfo());
    }

    @PostMapping
    public ResponseEntity<?> registerBooks(@RequestBody RegisterBookRequest request) {
        
        // 책 등록 처리
        log.info(request.toString());
        return ResponseEntity.noContent().build();
    }

    private List<BookInfoBase> getBookInfo() {
        List<BookInfoBase> bookInfoBaseList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int num = i + 1;
            final BookInfoBase bookInfoBase = BookInfoBase.builder()
                .id((long) num)
                .title("Book" + num)
                .authors("kenux.yun")
                .datetime(OffsetDateTime.now().minusMonths(i))
                .build();
            bookInfoBaseList.add(bookInfoBase);
        }
        return bookInfoBaseList;
    }
}
