package me.kenux.travelog.domain.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.domain.booklog.entity.Book;
import me.kenux.travelog.domain.booklog.entity.BookLog;
import me.kenux.travelog.domain.booklog.entity.BookReview;
import me.kenux.travelog.domain.booklog.repository.BookLogRepository;
import me.kenux.travelog.domain.booklog.repository.BookRepository;
import me.kenux.travelog.domain.booklog.repository.BookReviewRepository;
import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.entity.UserPassword;
import me.kenux.travelog.domain.member.repository.MemberRepository;
import me.kenux.travelog.domain.member.repository.PasswordRepository;
import me.kenux.travelog.global.exception.CustomException;
import me.kenux.travelog.global.exception.ErrorCode;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

import static me.kenux.travelog.global.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Profile("local")
@Slf4j
public class TestInitService implements ApplicationListener<ApplicationStartedEvent> {

    private final MemberRepository memberRepository;
    private final PasswordRepository passwordRepository;
    private final PasswordEncoder passwordEncoder;
    private final BookRepository bookRepository;
    private final BookLogRepository bookLogRepository;
    private final BookReviewRepository bookReviewRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationStartedEvent event) {
        log.info("로컬 개발 환경 : 테스트 멤버 추가 ");
        insertAdmin();
        insertMember();
        insertBook();
        insertBookLog();
        insertBookReview();
    }

    private void insertAdmin() {
        final Member admin = Member.createAdmin("관리자", "admin@test.com");
        memberRepository.save(admin);

        UserPassword password = new UserPassword(admin, getEncodedPassword());
        passwordRepository.save(password);
    }

    private void insertMember() {
        createMember("user");
        for (int i = 0; i < 10; i++) {
            String username = "user" + 100 + i;
            createMember(username);
        }
    }

    private void createMember(String username) {
        String email = username + "@test.com";
        final Member newMember = Member.createNewMember(username, email);
        memberRepository.save(newMember);
        UserPassword password = new UserPassword(newMember, getEncodedPassword());
        passwordRepository.save(password);
    }

    private void insertBook() {
        for (int i = 0; i < 10; i++) {
            createBook("book" + i);
        }
    }

    private void createBook(String bookTitle) {
        final Book book = Book.builder()
                .title(bookTitle)
                .publishedDate(LocalDate.now())
                .publisher("베스트 지니어스")
                .isbn(getRandomLong(13))
                .authors("작가1")
                .build();
        final String thumbnailUrl = "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F2425256%3Ftimestamp%3D20230307154230";
        book.updateThumbnail(thumbnailUrl);
        book.updateContents("This is Sample book.");
        bookRepository.save(book);
    }

    public static String getRandomLong(int len) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        String result = "";

        for (int i = 0; i < len / 10; i++) {
            result += random.nextLong(1_000_000_000L, 10_000_000_000L);
        }

        if (result.length() != len) {
            if (result.length() < len) {
                result += random.nextLong(1_000_000_000L, 10_000_000_000L);
            }
            result = result.substring(0, len);
        }
        return result;
    }

    private void insertBookLog() {
        final Member member = getMember();
        bookRepository.findAll().forEach(book -> {
            final BookLog newLog = BookLog.createNewLog(book, member);
            bookLogRepository.save(newLog);
        });
    }

    private void insertBookReview() {
        final Member member = getMember();
        final Book book = getBook();
        final BookReview bookReview =
                BookReview.createBookReview(book, member, "This book is best for me.", 10);
        bookReviewRepository.save(bookReview);
    }

    private Member getMember() {
        return memberRepository.findById(2L)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_EXIST));
    }

    private Book getBook() {
        return bookRepository.findById(1L)
                .orElseThrow(() -> new CustomException(BOOK_NOT_FOUND));
    }

    private String getEncodedPassword() {
        return passwordEncoder.encode("1");
    }
}
