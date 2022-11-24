package me.kenux.travelog.repository.cond;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TravelHistorySearchCond {

    private String region;

    private LocalDate startDate;
    private LocalDate endDate;
}
