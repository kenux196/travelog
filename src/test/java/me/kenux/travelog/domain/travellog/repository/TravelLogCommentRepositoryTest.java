package me.kenux.travelog.domain.travellog.repository;

import me.kenux.travelog.BaseRepositoryConfig;
import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.entity.UserPassword;
import me.kenux.travelog.domain.travellog.entity.TravelLog;
import me.kenux.travelog.domain.travellog.entity.enums.TravelType;
import me.kenux.travelog.domain.travellog.repository.dto.TravelLogSearchCond;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TravelLogCommentRepositoryTest extends BaseRepositoryConfig {

    @Autowired
    TravelLogRepository travelLogRepository;

    private static final Long MEMBER_ID = 1L;

    @BeforeEach
    void setup() {
        final TravelLog travelLog = getTravelLog();
        travelLogRepository.save(travelLog);
    }

    @Test
    void findAllByCondition_Without_Condition() {
        // given
        TravelLogSearchCond cond = new TravelLogSearchCond();

        // when
        final List<TravelLog> result = travelLogRepository.findAllByCondition(cond);

        // then
        assertThat(result).hasSize(1);
    }

    @Test
    void findAllByCondition_With_Member() {
        // given
        TravelLogSearchCond cond = new TravelLogSearchCond();
        cond.setMemberId(MEMBER_ID);

        // when
        final List<TravelLog> result = travelLogRepository.findAllByCondition(cond);

        // then
        assertThat(result).hasSize(1);
    }

    private static TravelLog getTravelLog() {
        return TravelLog.builder()
                .title("팔공산 등산")
                .content("힘들었지만, 즐겁다.")
                .startDate(LocalDate.of(2022, 10, 1))
                .duration(3)
                .travelType(TravelType.CAMPING)
                .memberId(MEMBER_ID)
                .build();
    }

}