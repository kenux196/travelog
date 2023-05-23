package me.kenux.travelog.domain.booklog.service.dto;

import lombok.Builder;
import lombok.Data;
import me.kenux.travelog.domain.booklog.entity.Book;

import java.time.LocalDate;

@Data
public class BookInfoDto {

    @Builder
    public record Basic(Long id, String title, String authors, String publisher,
                        LocalDate publishedDate, String thumbnail) {

        public static Basic from(Book book) {
            return Basic.builder()
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
    public record Simple(Long id, String title, String thumbnail) {

        public static Simple from(Book book) {
            return Simple.builder()
                    .id(book.getId())
                    .title(book.getTitle())
                    .thumbnail(book.getThumbnail())
                    .build();
        }
    }

    @Builder
    public record WithRating(Long id, String title, String authors, String publisher,
                            LocalDate publishedDate, String thumbnail, float rating) {

        public static WithRating from(Book book, float rating) {
            return WithRating.builder()
                    .id(book.getId())
                    .title(book.getTitle())
                    .authors(book.getAuthors())
                    .publisher(book.getPublisher())
                    .publishedDate(book.getPublishedDate())
                    .thumbnail(book.getThumbnail())
                    .rating(rating)
                    .build();
        }
    }
}
