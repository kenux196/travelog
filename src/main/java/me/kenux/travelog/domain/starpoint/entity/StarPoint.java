package me.kenux.travelog.domain.starpoint.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kenux.travelog.domain.destination.entity.Destination;

import javax.persistence.*;

@Entity
@Table(name = "start_point")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class StarPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer starPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id")
    private Destination destination;

    public StarPoint(Integer starPoint, Destination camp) {
        this.starPoint = starPoint;
        this.destination = camp;
    }
}
