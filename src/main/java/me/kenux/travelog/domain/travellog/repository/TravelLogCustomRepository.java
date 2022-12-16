package me.kenux.travelog.domain.travellog.repository;

import me.kenux.travelog.domain.travellog.entity.TravelLog;
import me.kenux.travelog.domain.travellog.repository.dto.TravelLogSearchCond;

import java.util.List;

public interface TravelLogCustomRepository {

    List<TravelLog> findAllByCondition(TravelLogSearchCond cond);
}
