package me.kenux.travelog.domain.booklog.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import me.kenux.travelog.domain.booklog.entity.Book;
import org.springframework.util.StringUtils;

import java.time.OffsetDateTime;
import java.util.List;

@Schema(description = "책 추가 요청 메시지")
@Data
public class AddBookRequest {

    List<BookInfo> books;

    @Schema(description = "책 정보 메시지")
    @Data
    public static class BookInfo {
        @NotBlank
        private String title;

        @NotBlank
        private String authors;

        @NotBlank
        private String isbn;

        @NotBlank
        private String publisher;

        @NotNull
        private OffsetDateTime datetime;

        private String contents;

        private String thumbnail;

        public static Book toEntity(BookInfo request) {
            final Book book = Book.createNewBook(
                    request.getTitle(), request.getAuthors(), request.getIsbn(),
                    request.getDatetime().toLocalDate(), request.getPublisher());

            if (StringUtils.hasText(request.getContents())) {
                book.updateContents(request.getContents());
            }

            if (StringUtils.hasText(book.getThumbnail())) {
                book.updateThumbnail(request.getThumbnail());
            }
            return book;
        }
    }
}
