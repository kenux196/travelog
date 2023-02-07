package me.kenux.travelog.domain.mytest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import me.kenux.travelog.global.config.QueryDslConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(QueryDslConfig.class)
@Profile("local")
class TestItemRepository {

    @PersistenceContext
    EntityManager em;

    private final String ISBN = "123-123";

    @Test
    @Transactional
    void insertAndFind() {
//        final Book.BookId bookId = new Book.BookId();
//        bookId.setIsbn("1233-11");
//        bookId.setName("JPA book");
//
//        final Book book = new Book();
//        book.setBookId(bookId);
//        book.setPrice(10000);
//
//        em.persist(book);
//        em.flush();
//        em.clear();

        insertData();

//        final Book foundedItem = em.find(Book.class, bookId);
//        final Book book1 = em.find(Book.class, bookId.getIsbn());
//        System.out.println("book1 = " + book1);

        String query = "select b from Book b where b.bookId.isbn = :isbn";
        final List<Book> resultList = em.createQuery(query, Book.class)
            .setParameter("isbn", ISBN)
            .getResultList();
        assertThat(resultList).hasSize(3);
        for (Book book : resultList) {
            System.out.println("book = " + book);
        }
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            final Book book = createBook(ISBN, "book" + i);
            em.persist(book);
        }
        em.flush();
        em.clear();
    }

    private Book createBook(String isbn, String name) {
        final Book.BookId bookId = new Book.BookId();
        bookId.setIsbn(isbn);
        bookId.setName(name);

        final Book book = new Book();
        book.setBookId(bookId);
        book.setPrice(10000);

        return book;
    }
}
