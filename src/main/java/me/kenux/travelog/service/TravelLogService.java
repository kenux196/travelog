package me.kenux.travelog.service;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.TravelLog;
import me.kenux.travelog.global.exception.CustomException;
import me.kenux.travelog.global.exception.ErrorCode;
import me.kenux.travelog.repository.MemberRepository;
import me.kenux.travelog.repository.TravelLogRepository;
import me.kenux.travelog.service.dto.request.TravelLogSaveRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TravelLogService {

    private final TravelLogRepository travelLogRepository;
    private final MemberRepository memberRepository;

    public void saveTravelLog(TravelLogSaveRequest request) {
        if (!memberRepository.existsById(request.getMemberId())) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }

        final TravelLog travelLog = request.toEntity();
        travelLogRepository.save(travelLog);
    }
}
