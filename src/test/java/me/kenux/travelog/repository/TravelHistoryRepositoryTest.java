package me.kenux.travelog.repository;

import me.kenux.travelog.domain.Member;
import me.kenux.travelog.domain.TravelHistory;
import net.bytebuddy.asm.MemberRemoval;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
//@Rollback(value = false)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TravelHistoryRepositoryTest {

    @Autowired
    private TravelHistoryRepository travelHistoryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("여행 히스토리 저장 테스트")
    void save() {
        // given
        Member member = new Member("kenux", "kenux.yun@gmail.com");
        memberRepository.save(member);

        final TravelHistory history = TravelHistory.builder()
            .title("팔공산 등산")
            .content("힘들었지만, 즐겁다.")
            .startDate(LocalDate.of(2022, 10, 1))
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

        final TravelHistory history = TravelHistory.builder()
            .title("팔공산 등산")
            .content("힘들었지만, 즐겁다.")
            .startDate(LocalDate.of(2022, 10, 1))
            .member(member)
            .build();

        // when
        travelHistoryRepository.save(history);

        // then
        assertThat(history.getCreatedDate()).isNotNull();
        assertThat(history.getUpdatedDate()).isNotNull();
    }
}
