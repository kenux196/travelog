package me.kenux.travelog.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import me.kenux.travelog.domain.TravelHistory;
import me.kenux.travelog.domain.enums.TravelType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class TravelHistoryDto {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    private LocalDate endDate;

    private TravelType travelType;

    public TravelHistory toEntity() {
        return TravelHistory.builder()
            .title(title)
            .content(content)
            .startDate(startDate)
            .endDate(endDate)
            .travelType(travelType)
            .build();
    }
}
