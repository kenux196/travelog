package me.kenux.travelog.service;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.TravelHistory;
import me.kenux.travelog.repository.TravelHistoryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TravelHistoryService {

    private final TravelHistoryRepository travelHistoryRepository;

    public void save(TravelHistory history) {
        travelHistoryRepository.save(history);
    }
}
