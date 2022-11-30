package me.kenux.travelog.repository;

import me.kenux.travelog.domain.Member;
import me.kenux.travelog.domain.TravelLog;
import me.kenux.travelog.domain.enums.TravelType;
import me.kenux.travelog.global.config.QueryDslConfig;
import me.kenux.travelog.repository.base.RepositoryTest;
import me.kenux.travelog.repository.cond.TravelLogSearchCond;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class TravelLogRepositoryTest extends RepositoryTest {

    @Autowired
    private TravelLogRepository travelLogRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void beforeEach() {
        final Member member = getMember();
        memberRepository.save(member);
    }

    @Test
    @DisplayName("제목은 필수이다.")
    void titleIsNotNull() {
//        Member member = getMember();
//        memberRepository.save(member);

        final TravelLog history = TravelLog.builder()
            .content("힘들었지만, 즐겁다.")
            .travelType(TravelType.CAMPING)
            .startDate(LocalDate.now())
            .memberId(1L)
            .build();

        assertThatThrownBy(() -> travelLogRepository.save(history))
            .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("여행 타입은 필수이다.")
    void travelTypeIsNotNull() {
        final TravelLog history = TravelLog.builder()
            .title("title")
            .content("content")
            .memberId(1L)
            .build();
        assertThatThrownBy(() -> travelLogRepository.save(history))
            .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("멤버 아이디 필수이다.")
    void memberIdIsNotNull() {
        final TravelLog history = TravelLog.builder()
            .title("title")
            .content("content")
            .travelType(TravelType.PICNIC)
            .memberId(1L)
            .build();
        assertThatThrownBy(() -> travelLogRepository.save(history))
            .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("시작일은 필수이다.")
    void startDateIsNotNull() {
        Member member = getMember();
        memberRepository.save(member);

        final TravelLog history = TravelLog.builder()
            .title("팔공산 등산")
            .content("힘들었지만, 즐겁다.")
            .travelType(TravelType.CAMPING)
            .memberId(1L)
            .build();

        assertThatThrownBy(() -> travelLogRepository.save(history))
            .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("여행 히스토리 저장 테스트")
    void save() {
        // given
        final TravelLog history = getTravelLog();

        // when
        travelLogRepository.save(history);

        // then
        assertThat(history.getId()).isNotNull();
    }

    @Test
    @DisplayName("Auditing test")
    void saveWithAuditing() {
        // given
        final TravelLog history = getTravelLog();

        // when
        travelLogRepository.save(history);

        // then
        assertThat(history.getCreatedDate()).isNotNull();
        assertThat(history.getUpdatedDate()).isNotNull();
    }

    @Test
    @DisplayName("사용자가 작성한 여행로그를 찾는다.")
    void findAllTravelLogByMemberId() {
        // given
        final TravelLog travelLog = getTravelLog();
        travelLogRepository.save(travelLog);

        // when
        TravelLogSearchCond cond = new TravelLogSearchCond();
        cond.setMemberId(travelLog.getMemberId());
        final List<TravelLog> result = travelLogRepository.findAllByCondition(cond);

        // then
        assertThat(result).hasSize(1);
    }

    private static Member getMember() {
        return Member.createNewMember("kenux", "kenux.yun@gmail.com");
    }

    private static TravelLog getTravelLog() {
        return TravelLog.builder()
            .title("팔공산 등산")
            .content("힘들었지만, 즐겁다.")
            .startDate(LocalDate.of(2022, 10, 1))
            .travelType(TravelType.CAMPING)
            .memberId(1L)
            .build();
    }
}
