package me.kenux.travelog.domain.book.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    private static final String title = "book title";
    private static final String authors = "author";
    private static final String isbn = "123123123";
    private static final String publisher = "publisher";
    private static final LocalDate publishedDate = LocalDate.of(2023, 1, 1);

    @Test
    @DisplayName("북 객체 생성 static method")
    void createNewBook() {
        final Book newBook = createBook();

        assertThat(newBook).isNotNull();
        assertThat(newBook).isInstanceOf(Book.class);
    }

    private Book createBook() {
        return Book.createNewBook(title, authors, isbn, publishedDate, publisher);
    }

    @Test
    @DisplayName("isbn과 제목이 일치하면 동일한 책이다.")
    void isSameBook() {
        // given
        final Book book = createBook();

        // when
        final boolean sameBook = book.isSameBook(title, isbn);

        // then
        assertThat(sameBook).isTrue();
    }

    @Test
    @DisplayName("isbn이 동일하더라도 제목이 다르면 다른책이다.")
    void isNotSameBook_different_title() {
        // given
        final Book book = createBook();

        // when
        final boolean sameBook = book.isSameBook("diff title", isbn);

        // then
        assertThat(sameBook).isFalse();
    }

    @Test
    @DisplayName("isbn이 다르면 다른 책이다.")
    void isNotSameBook_different_isbn() {
        // given
        final Book book = createBook();

        // when
        final boolean sameBook = book.isSameBook(title, "diff isbn");

        // then
        assertThat(sameBook).isFalse();
    }

    @Test
    void updateContentAndUpdateThumbnail() {
        final Book book = createBook();

        book.updateContents("new content");
        book.updateThumbnail("new image");


        assertThat(book.getContents()).isEqualTo("new content");
        assertThat(book.getThumbnail()).isEqualTo("new image");

    }
}