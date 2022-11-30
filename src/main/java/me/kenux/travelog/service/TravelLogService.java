package me.kenux.travelog.service;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.TravelLog;
import me.kenux.travelog.repository.TravelLogRepository;
import me.kenux.travelog.service.dto.request.TravelLogSaveRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TravelLogService {

    private final TravelLogRepository travelLogRepository;

    public void registerTravelLog(TravelLogSaveRequest request) {
        final TravelLog travelLog = request.toEntity();
        travelLogRepository.save(travelLog);
    }
}
