package me.kenux.travelog.domain.travellog.service.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import me.kenux.travelog.domain.travellog.entity.TravelLog;
import me.kenux.travelog.domain.travellog.entity.enums.TravelType;

import java.time.LocalDate;

@Data
public class TravelLogSaveRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull
    private Integer duration;

    private TravelType travelType;

    private Long memberId;

    public TravelLog toEntity() {
        return TravelLog.builder()
            .title(title)
            .content(content)
            .startDate(startDate)
            .duration(duration)
            .travelType(travelType)
            .memberId(memberId)
            .build();
    }
}
