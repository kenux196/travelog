package me.kenux.travelog.domain.booklog.repository;

import me.kenux.travelog.RepositoryTestConfigure;
import me.kenux.travelog.domain.booklog.entity.Book;
import me.kenux.travelog.domain.booklog.entity.BookReview;
import me.kenux.travelog.domain.booklog.repository.dto.BookReviewSearchCond;
import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.entity.UserPassword;
import me.kenux.travelog.domain.member.repository.MemberRepository;
import me.kenux.travelog.domain.member.repository.PasswordRepository;
import me.kenux.travelog.global.exception.CustomException;
import me.kenux.travelog.global.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class BookReviewRepositoryTest extends RepositoryTestConfigure {

    @Autowired
    private BookReviewRepository bookReviewRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordRepository passwordRepository;

    private Member member;
    private Book book;

    private static final String email = "member@email.com";
    private static final String isbn = "isbn";

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
        member = Member.createNewMember("member1", email, password);
        memberRepository.save(member);
    }

    private void saveTestBookData() {
        LocalDate date = LocalDate.of(2023, 3, 30);
        book = Book.createNewBook("title", "author", isbn, date, "publisher");
        bookRepository.save(book);
    }

    @Test
    @DisplayName("find book review by bookId")
    void findByBookId() {
        // when
        List<BookReview> result = bookReviewRepository.findByBookId(book.getId());

        // then
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("find book review by memberId")
    void findByMemberId() {
        // when
        final List<BookReview> result = bookReviewRepository.findByMemberId(member.getId());

        // then
        assertThat(result).hasSize(1);
    }

    @ParameterizedTest(name = "{index}: {2}")
    @MethodSource("provideBookReviewSearchCond")
    @DisplayName("find book review by search condition")
    void findReviewByCondition(String email, String isbn, String message, int count) {
        // given
        Long memberId = null;
        Long bookId = null;
        if (StringUtils.hasText(email)) {
            member = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_EXIST));
            memberId = member.getId();
        }
        if (StringUtils.hasText(isbn)) {
            final Book book = bookRepository.findBookByIsbn(isbn)
                    .orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));
            bookId = book.getId();
        }
        final BookReviewSearchCond cond = new BookReviewSearchCond(memberId, bookId);

        // when
        final List<BookReview> result = bookReviewRepository.findReviewByCondition(cond);

        // then
        assertThat(result).hasSize(count);
    }

    private static Stream<Arguments> provideBookReviewSearchCond() {
        System.out.println("provideBookReviewSearchCond() = ");
        return Stream.of(
                Arguments.of(null, null, "no condition - find all", 1),
                Arguments.of(email, null, "find by memberId", 1),
                Arguments.of(null, isbn, "find by bookId", 1),
                Arguments.of(email, isbn, "find by both(member, book)", 1)
        );
    }

    @Test
    @DisplayName("find book review with member data")
    void findReviewByBook() {
        // when
        final List<BookReview> reviews = bookReviewRepository.findReviewWithMemberBy(book.getId());

        // then
        assertThat(reviews).hasSize(1);
        assertThat(reviews.get(0).getMember()).isInstanceOf(Member.class);
    }
}
