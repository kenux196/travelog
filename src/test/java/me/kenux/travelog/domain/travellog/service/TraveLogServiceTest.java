package me.kenux.travelog.domain.travellog.service;

import me.kenux.travelog.domain.member.repository.MemberRepository;
import me.kenux.travelog.domain.travellog.repository.TravelLogRepository;
import me.kenux.travelog.domain.travellog.service.dto.request.TravelLogSaveRequest;
import me.kenux.travelog.global.exception.CustomException;
import me.kenux.travelog.global.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class TraveLogServiceTest {

    @Mock
    MemberRepository memberRepository;
    @Mock
    TravelLogRepository travelLogRepository;

    @InjectMocks
    TraveLogService traveLogService;

    @Test
    @DisplayName("익명 사용자의 글 등록은 예외가 발생해야 한다.")
    void save_travelLog_exception_member_not_founded() {
        // given
        TravelLogSaveRequest request = new TravelLogSaveRequest();
        given(memberRepository.existsById(any())).willReturn(false);

        // when then
        assertThatThrownBy(() -> traveLogService.saveTravelLog(request))
            .isInstanceOf(CustomException.class)
            .hasMessage(ErrorCode.MEMBER_NOT_EXIST.getMessage());
    }

    @Test
    @DisplayName("여행 기록 정상 등록되어야 한다.")
    void saveTravelLog_success() {
        // given
        TravelLogSaveRequest request = mock(TravelLogSaveRequest.class);
        given(memberRepository.existsById(any())).willReturn(true);

        // when
        traveLogService.saveTravelLog(request);

        // then
        then(travelLogRepository).should().save(any());
    }


}
