package me.kenux.travelog.service;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.TravelLog;
import me.kenux.travelog.repository.TravelHistoryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TravelHistoryService {

    private final TravelHistoryRepository travelHistoryRepository;

    public void save(TravelLog history) {
        travelHistoryRepository.save(history);
    }
}
