package me.kenux.travelog.domain.starpoint.entity;

import me.kenux.travelog.domain.booklog.entity.Book;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class BookStarPointTest {

    @Test
    void createTest() {
        // given
        LocalDate publishDate = LocalDate.of(2022, 1, 20);
        final Book book = Book
            .createNewBook("book1", "author1", "isbn", publishDate, "publisher1");
        int point = 10;

        // when
        BookStarPoint bookStarPoint = BookStarPoint.createStarPoint(book, point);

        // then
        assertThat(bookStarPoint).isNotNull();
    }
}
