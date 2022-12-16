package me.kenux.travelog.domain.travellog.repository.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TravelLogSearchCond {

    private Long memberId;

    private String region;

    private LocalDate startDate;

    private LocalDate endDate;
}
