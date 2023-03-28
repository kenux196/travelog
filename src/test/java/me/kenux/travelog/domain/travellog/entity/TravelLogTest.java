package me.kenux.travelog.domain.travellog.entity;

import me.kenux.travelog.domain.travellog.entity.enums.TravelType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class TravelLogTest {

    @Test
    @DisplayName("여행 기간을 수정할 수 있다.")
    void updateDurationOfTrip() {
        // given
        final TravelLog travelLog = TravelLog.builder()
            .title("title")
            .contents("content")
            .startDate(LocalDate.of(2022, 12, 1))
            .duration(3)
            .build();

        // when
        final LocalDate updatedDate = LocalDate.of(2022, 12, 3);
        int duration = 3;
        travelLog.changeDurationOfTheTrip(updatedDate, duration);

        // then
        assertThat(travelLog.getStartDate()).isEqualTo(updatedDate);
        assertThat(travelLog.getDuration()).isEqualTo(duration);
    }

    @Test
    @DisplayName("여행 종료일을 구할 수 있다.")
    void getEndDate() {
        // given
        final LocalDate startDate = LocalDate.of(2022, 12, 1);
        int duration = 3;
        final TravelLog travelLog = TravelLog.builder()
            .title("title")
            .contents("content")
            .startDate(startDate)
            .duration(duration)
            .build();

        // when
        final LocalDate endDate = travelLog.getEndDate();

        // then
        assertThat(endDate).isEqualTo(startDate.plusDays(duration));
    }

    @Test
    @DisplayName("여행 타입 변경")
    void changeTravelType() {
        // given
        final TravelLog travelLog = TravelLog.builder()
            .title("title")
            .contents("content")
            .travelType(TravelType.TOURISM)
            .build();

        // when
        travelLog.changeTravelType(TravelType.PICNIC);

        // then
        assertThat(travelLog.getTravelType()).isEqualTo(TravelType.PICNIC);
    }
}
