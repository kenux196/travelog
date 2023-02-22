package me.kenux.travelog.web.api.book;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This is test for data
 */

@RestController
@RequestMapping("/api/book")
public class BookController {

    @GetMapping
    public ResponseEntity<?> getBooks() {
        return ResponseEntity.ok(getBookInfo());
    }

    private List<BookInfoDto> getBookInfo() {
        List<BookInfoDto> bookInfoDtoList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int num = i + 1;
            final BookInfoDto bookInfoDto = BookInfoDto.builder()
                .id((long) num)
                .title("Book" + num)
                .author("kenux.yun")
                .createdDate(OffsetDateTime.now().minusMonths(i))
                .build();
            bookInfoDtoList.add(bookInfoDto);
        }
        return bookInfoDtoList;
    }

    @Data
    @Builder
    public static class BookInfoDto {
        private Long id;
        private String title;
        private String author;
        private OffsetDateTime createdDate;
    }
}
