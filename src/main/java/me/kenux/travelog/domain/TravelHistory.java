package me.kenux.travelog.domain;

import lombok.*;
import me.kenux.travelog.domain.enums.TravelType;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "travel_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class TravelHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private TravelType travelType;

    private String content;

    // TODO - 이미지 등 추가 기능 구현 2022-10-21 skyun

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id", referencedColumnName = "id")
    private Destination destination;
}
