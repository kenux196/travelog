package me.kenux.travelog.domain.travellog.repository;

import jakarta.validation.ConstraintViolationException;
import me.kenux.travelog.BaseRepositoryConfig;
import me.kenux.travelog.domain.member.repository.MemberRepository;
import me.kenux.travelog.domain.member.repository.PasswordRepository;
import me.kenux.travelog.domain.travellog.entity.TravelLog;
import me.kenux.travelog.domain.travellog.entity.enums.TravelType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class TravelLogBaseRepositoryTest extends BaseRepositoryConfig {

    @Autowired
    private TravelLogRepository travelLogRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordRepository passwordRepository;

    @Test
    @DisplayName("제목은 필수이다.")
    void titleIsNotNull() {
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

    private static TravelLog getTravelLog() {
        return TravelLog.builder()
            .title("팔공산 등산")
            .content("힘들었지만, 즐겁다.")
            .startDate(LocalDate.of(2022, 10, 1))
            .duration(3)
            .travelType(TravelType.CAMPING)
            .memberId(1L)
            .build();
    }
}
