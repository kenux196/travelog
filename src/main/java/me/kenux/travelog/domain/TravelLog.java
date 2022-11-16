package me.kenux.travelog.domain;

import lombok.*;
import me.kenux.travelog.domain.enums.TravelType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "travel_log")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class TravelLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TravelType travelType;

    private String content;

    // TODO - 이미지 등 추가 기능 구현 2022-10-21 skyun

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id")
    private Destination destination;

    @OneToMany(mappedBy = "travelLog")
    @Builder.Default
    private List<TravelLogComment> comments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "member_id")
    @NotNull
    private Member member;
}
