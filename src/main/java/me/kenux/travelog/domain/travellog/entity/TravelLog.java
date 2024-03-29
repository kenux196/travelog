package me.kenux.travelog.domain.travellog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import me.kenux.travelog.domain.common.BaseEntity;
import me.kenux.travelog.domain.destination.entity.Destination;
import me.kenux.travelog.domain.travellog.entity.enums.TravelType;

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

    @NotNull
    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar")
    @NotNull
    private TravelType travelType;

    @Column(name = "contents", length = 5000)
    private String contents;

    @Column(name = "member_id", nullable = false)
    @NotNull
    private Long memberId;

    // TODO - 이미지 등 추가 기능 구현 2022-10-21 skyun

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id")
    private Destination destination;

    @OneToMany(mappedBy = "travelLog")
    @Builder.Default
    private List<TravelLogComment> comments = new ArrayList<>();

    // TODO - 연관관계가 필요한 경우에 설정한다. 현재는 연관관계가 필요없다고 보인다. 2022-11-30 skyun
//    @ManyToOne
//    @JoinColumn(name = "member_id", insertable = false, updatable = false)
//    private Member member;


    public void changeDurationOfTheTrip(LocalDate startDate, int duration) {
        this.startDate = startDate;
        this.duration = duration;
    }

    public LocalDate getEndDate() {
        return startDate.plusDays(duration);
    }

    public void changeTravelType(TravelType travelType) {
        this.travelType = travelType;
    }
}
