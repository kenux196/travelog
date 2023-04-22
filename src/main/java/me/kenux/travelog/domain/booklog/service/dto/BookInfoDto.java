package me.kenux.travelog.domain.booklog.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import me.kenux.travelog.domain.booklog.entity.Book;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.OffsetDateTime;
@Data
@Builder
public class BookInfoDto {
    private Long id;

    private String title;

    private String authors;

    private String isbn;

    private String publisher;

    private OffsetDateTime datetime;

    private String contents;

    private String thumbnail;

    private float rating;

    @Builder
    public record BasicInfo(Long id, String title, String authors, String publisher,
                            LocalDate publishedDate, String thumbnail) {

        public static BasicInfo from(Book book) {
            return BasicInfo.builder()
                    .id(book.getId())
                    .title(book.getTitle())
                    .authors(book.getAuthors())
                    .publisher(book.getPublisher())
                    .publishedDate(book.getPublishedDate())
                    .thumbnail(book.getThumbnail())
                    .build();
        }
    }

    @Builder
    public record SimpleInfo(Long id, String title, String thumbnail) {

        public static SimpleInfo from(Book book) {
            return SimpleInfo.builder()
                    .id(book.getId())
                    .title(book.getTitle())
                    .thumbnail(book.getThumbnail())
                    .build();
        }
    }

}
