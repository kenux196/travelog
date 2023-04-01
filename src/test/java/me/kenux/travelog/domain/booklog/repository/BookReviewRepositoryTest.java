package me.kenux.travelog.domain.booklog.repository;

import me.kenux.travelog.BaseRepositoryConfig;
import me.kenux.travelog.domain.booklog.entity.Book;
import me.kenux.travelog.domain.booklog.entity.BookReview;
import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.entity.UserPassword;
import me.kenux.travelog.domain.member.repository.MemberRepository;
import me.kenux.travelog.domain.member.repository.PasswordRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BookReviewRepositoryTest extends BaseRepositoryConfig {

    @Autowired
    private BookReviewRepository bookReviewRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordRepository passwordRepository;

    private Book book;
    private Member member;

    @BeforeEach
    void setup() {
        saveTestMemberData();
        saveTestBookData();
        final BookReview review = BookReview.createBookReview(book, member, "review", 3);
        bookReviewRepository.save(review);
    }

    private void saveTestMemberData() {
        final UserPassword password = new UserPassword("password");
        passwordRepository.save(password);
        member = Member.createNewMember("member1", "member1@email.com", password);
        memberRepository.save(member);
    }

    private void saveTestBookData() {
        LocalDate date = LocalDate.of(2023, 3, 30);
        book = Book.createNewBook("title", "author", "isbn", date, "publisher");
        bookRepository.save(book);
    }

    @Test
    @DisplayName("find book_review data by book_id")
    void findByBookId() {
        // given
        final Long bookId = book.getId();

        // when
        List<BookReview> result = bookReviewRepository.findByBookId(bookId);

        // then
        assertThat(result).hasSize(1);
    }
}