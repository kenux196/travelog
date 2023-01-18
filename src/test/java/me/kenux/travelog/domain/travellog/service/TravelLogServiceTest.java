package me.kenux.travelog.domain.travellog.service;

import me.kenux.travelog.domain.member.repository.MemberRepository;
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

@ExtendWith(MockitoExtension.class)
class TravelLogServiceTest {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    TravelLogService travelLogService;

    @Test
    @DisplayName("존재하지 않는 회원의 글등록은 예외가 발생한다.")
    void save_travelLog_exception_member_not_founded() {
        // given
        TravelLogSaveRequest request = new TravelLogSaveRequest();
        given(memberRepository.existsById(any())).willReturn(false);

        // when then
        assertThatThrownBy(() -> travelLogService.saveTravelLog(request))
            .isInstanceOf(CustomException.class)
            .hasMessage(ErrorCode.MEMBER_NOT_FOUND.getMessage());
    }


}
