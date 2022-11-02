package me.kenux.travelog.repository;

import me.kenux.travelog.domain.TravelHistory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TravelHistoryRepositoryTest {

    @Autowired
    private TravelHistoryRepository travelHistoryRepository;

    @Test
    @DisplayName("여행 히스토리 저장 테스트")
    void save() {
        // given
        final TravelHistory history = TravelHistory.builder()
            .title("팔공산 등산")
            .content("힘들었지만, 즐겁다.")
            .startDate(LocalDate.of(2022, 10, 1))
            .build();

        // when
        travelHistoryRepository.save(history);

        // then
        assertThat(history.getId()).isNotNull();
    }
}
