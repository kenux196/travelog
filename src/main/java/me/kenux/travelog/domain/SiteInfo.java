package me.kenux.travelog.domain;

import lombok.*;
import me.kenux.travelog.domain.enums.SiteType;

import javax.persistence.*;

@Entity
@Table(name = "site_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class SiteInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String siteNumber;

    private Integer with;

    private Integer height;

    private Integer price;

    @Enumerated(EnumType.STRING)
    private SiteType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "camp_id", referencedColumnName = "id")
    private Camp camp;
}
