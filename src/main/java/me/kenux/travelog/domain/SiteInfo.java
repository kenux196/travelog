package me.kenux.travelog.domain;

import lombok.*;
import me.kenux.travelog.domain.enums.SiteType;
import net.bytebuddy.build.ToStringPlugin;

import javax.persistence.*;

@Entity
@Table(name = "site_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@ToString
public class SiteInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String siteNumber;

    private Integer width;

    private Integer height;

    private Integer price;

    @Enumerated(EnumType.STRING)
    private SiteType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "camp_id", referencedColumnName = "id")
    @ToString.Exclude
    private Camp camp;

    public void setCamp(Camp camp) {
        this.camp = camp;
        camp.siteInfos.add(this);
    }
}
