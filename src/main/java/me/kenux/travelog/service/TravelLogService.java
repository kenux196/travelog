package me.kenux.travelog.service;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.TravelLog;
import me.kenux.travelog.repository.TravelLogRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TravelLogService {

    private final TravelLogRepository travelLogRepository;

    public void registerTravelLog(TravelLog travelLog) {
        travelLogRepository.save(travelLog);
    }
}
