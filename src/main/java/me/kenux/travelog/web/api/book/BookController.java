package me.kenux.travelog.web.api.book;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.domain.book.service.BookManagementService;
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

    @GetMapping
    public ResponseEntity<?> getBooks() {
        return ResponseEntity.ok(getBookInfo());
    }

    @PostMapping
    public ResponseEntity<?> registerBooks(@RequestBody RegisterBookRequest request) {
        // 책 등록 처리
        log.info(request.toString());
        bookManagementService.registerBook(request);
        return ResponseEntity.noContent().build();
    }

    private List<BookInfoDto> getBookInfo() {
        List<BookInfoDto> bookInfoDtoList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int num = i + 1;
            final BookInfoDto bookInfoDto = BookInfoDto.builder()
                .id((long) num)
                .title("Book" + num)
                .authors("kenux.yun")
                .datetime(OffsetDateTime.now().minusMonths(i))
                .build();
            bookInfoDtoList.add(bookInfoDto);
        }
        return bookInfoDtoList;
    }
}
