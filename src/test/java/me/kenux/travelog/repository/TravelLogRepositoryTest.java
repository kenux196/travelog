package me.kenux.travelog.repository;

import me.kenux.travelog.domain.Member;
import me.kenux.travelog.domain.TravelLog;
import me.kenux.travelog.domain.enums.TravelType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@ActiveProfiles("test")
//@Rollback(value = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TravelLogRepositoryTest {

    @Autowired
    private TravelHistoryRepository travelHistoryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("제목은 필수이다.")
    void titleIsNotNull() {
        Member member = new Member("kenux", "kenux.yun@gmail.com");
        memberRepository.save(member);

        final TravelLog history = TravelLog.builder()
            .content("힘들었지만, 즐겁다.")
            .travelType(TravelType.CAMPING)
            .member(member)
            .startDate(LocalDate.now())
            .build();

        assertThatThrownBy(() -> travelHistoryRepository.save(history))
            .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("여행 타입은 필수이다.")
    void travelTypeIsNotNull() {
        final TravelLog history = TravelLog.builder()
            .title("title")
            .content("content")
            .build();
        assertThatThrownBy(() -> travelHistoryRepository.save(history))
            .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("멤버 아이디 필수이다.")
    void memberIdIsNotNull() {
        final TravelLog history = TravelLog.builder()
            .title("title")
            .content("content")
            .travelType(TravelType.PICNIC)
            .build();
        assertThatThrownBy(() -> travelHistoryRepository.save(history))
            .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("시작일은 필수이다.")
    void startDateIsNotNull() {
        Member member = new Member("kenux", "kenux.yun@gmail.com");
        memberRepository.save(member);

        final TravelLog history = TravelLog.builder()
            .title("팔공산 등산")
            .content("힘들었지만, 즐겁다.")
            .travelType(TravelType.CAMPING)
            .member(member)
            .build();

        assertThatThrownBy(() -> travelHistoryRepository.save(history))
            .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("여행 히스토리 저장 테스트")
    void save() {
        // given
        Member member = new Member("kenux", "kenux.yun@gmail.com");
        memberRepository.save(member);

        final TravelLog history = TravelLog.builder()
            .title("팔공산 등산")
            .content("힘들었지만, 즐겁다.")
            .startDate(LocalDate.of(2022, 10, 1))
            .travelType(TravelType.CAMPING)
            .member(member)
            .build();

        // when
        travelHistoryRepository.save(history);

        // then
        assertThat(history.getId()).isNotNull();
    }

    @Test
    @DisplayName("Auditing test")
    void saveWithAuditing() {
        // given
        Member member = new Member("kenux", "kenux.yun@gmail.com");
        memberRepository.save(member);

        final TravelLog history = TravelLog.builder()
            .title("팔공산 등산")
            .content("힘들었지만, 즐겁다.")
            .startDate(LocalDate.of(2022, 10, 1))
            .travelType(TravelType.CAMPING)
            .member(member)
            .build();

        // when
        travelHistoryRepository.save(history);

        // then
        assertThat(history.getCreatedDate()).isNotNull();
        assertThat(history.getUpdatedDate()).isNotNull();
    }
}
