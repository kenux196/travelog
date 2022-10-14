package me.kenux.travelog.domain;

import lombok.*;

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
    @JoinColumn(name = "camp_id", referencedColumnName = "id")
    private Camp camp;

    public StarPoint(Integer starPoint, Camp camp) {
        this.starPoint = starPoint;
        this.camp = camp;
    }
}
