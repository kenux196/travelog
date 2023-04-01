package me.kenux.travelog.domain.booklog.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class BookTest {

    private static final String title = "book title";
    private static final String authors = "author";
    private static final String isbn = "123123123";
    private static final String publisher = "publisher";
    private static final LocalDate publishedDate = LocalDate.of(2023, 1, 1);

    @Test
    void createNewBook_failed_titleIsNull() {
        assertThatThrownBy(() -> Book.createNewBook(null, authors, isbn, publishedDate, publisher))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void createNewBook_failed_authorIsNull() {
        assertThatThrownBy(() -> Book.createNewBook(title, null, isbn, publishedDate, publisher))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void createNewBook_failed_isbnIsNull() {
        assertThatThrownBy(() -> Book.createNewBook(title, authors, null, publishedDate, publisher))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void createNewBook_failed_publishDateIsNull() {
        assertThatThrownBy(() -> Book.createNewBook(title, authors, isbn, null, publisher))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void createNewBook_failed_publisherIsNull() {
        assertThatThrownBy(() -> Book.createNewBook(title, authors, isbn, publishedDate, null))
                .isInstanceOf(NullPointerException.class);
    }


    @Test
    @DisplayName("createNewBook - success")
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