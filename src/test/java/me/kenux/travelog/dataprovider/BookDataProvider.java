package me.kenux.travelog.dataprovider;

import jakarta.persistence.EntityManager;
import me.kenux.travelog.domain.booklog.entity.Book;

import java.time.LocalDate;


public record BookDataProvider(EntityManager em) {

    public static final String BOOK_TITLE = "Test Book";
    public static final String BOOK_AUTHOR = "author";
    public static final String BOOK_ISBN = "ISBN-123";
    public static final String BOOK_PUBLISHER = "publisher A";
    public static final LocalDate BOOK_PUBLISHED_DATE = LocalDate.of(2023, 3, 20);


    public Book provideBookData() {
        final Book book = Book.createNewBook(BOOK_TITLE, BOOK_AUTHOR, BOOK_ISBN, BOOK_PUBLISHED_DATE, BOOK_PUBLISHER);
        em.persist(book);
        return book;
    }
}
