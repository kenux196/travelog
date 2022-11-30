package me.kenux.travelog.repository;

import me.kenux.travelog.domain.TravelLog;
import me.kenux.travelog.repository.cond.TravelLogSearchCond;

import java.util.List;

public interface TravelLogCustomRepository {

    List<TravelLog> findAllByCondition(TravelLogSearchCond cond);
}
