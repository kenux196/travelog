package me.kenux.travelog.domain.booklog.entity;

import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.entity.UserPassword;
import me.kenux.travelog.domain.member.entity.enums.UserRole;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class BookRateTest {

    @Test
    void createTest() {
        // given
        LocalDate publishDate = LocalDate.of(2022, 1, 20);
        final Book book = Book.createNewBook(
            "book1", "author1", "isbn", publishDate, "publisher1");
        final Member member = Member.builder()
            .name("member1")
            .email("member1@test.com")
            .userRole(UserRole.USER)
            .build();
        int point = 10;

        // when
        BookRate bookRate = BookRate.createBookRate(book, member, (short) point);

        // then
        assertThat(bookRate.getBook()).isEqualTo(book);
        assertThat(bookRate.getMember()).isEqualTo(member);
    }
}
