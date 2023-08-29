package me.kenux.travelog.domain.travellog.service;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.member.repository.MemberRepository;
import me.kenux.travelog.domain.travellog.entity.TravelLog;
import me.kenux.travelog.domain.travellog.repository.TravelLogRepository;
import me.kenux.travelog.domain.travellog.service.dto.request.TravelLogSaveRequest;
import me.kenux.travelog.global.exception.CustomException;
import me.kenux.travelog.global.exception.ErrorCode;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TraveLogService {

    private final TravelLogRepository travelLogRepository;
    private final MemberRepository memberRepository;

    public void saveTravelLog(TravelLogSaveRequest request) {
        if (!memberRepository.existsById(request.getMemberId())) {
            throw new CustomException(ErrorCode.MEMBER_NOT_EXIST);
        }

        final TravelLog travelLog = request.toEntity();
        travelLogRepository.save(travelLog);
    }
}
