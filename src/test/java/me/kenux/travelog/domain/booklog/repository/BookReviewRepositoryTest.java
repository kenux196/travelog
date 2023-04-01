package me.kenux.travelog.domain.booklog.repository;

import me.kenux.travelog.BaseRepositoryConfig;
import me.kenux.travelog.domain.booklog.entity.Book;
import me.kenux.travelog.domain.booklog.entity.BookReview;
import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.entity.UserPassword;
import me.kenux.travelog.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookReviewRepositoryTest extends BaseRepositoryConfig {

    @Autowired
    private BookReviewRepository bookReviewRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setup() {
        LocalDate date = LocalDate.of(2023, 3, 30);
        final Book book = Book.createNewBook("title", "author", "isbn", date, "publisher");
        bookRepository.save(book);
        final Member member = Member.createNewMember("member1", "member1@email.com", new UserPassword("password"));
        memberRepository.save(member);
        final BookReview review = BookReview.createBookReview(book, member, "review", 3);
        bookReviewRepository.save(review);
    }

    @Test
    void findByBookId() {
        // given


        // when

        // then
    }
}