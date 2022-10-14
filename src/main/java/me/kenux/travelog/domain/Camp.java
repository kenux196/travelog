package me.kenux.travelog.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "camp")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@ToString
public class Camp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address1;

    private String address2;

    @OneToMany(mappedBy = "camp")
    @Builder.Default
    List<SiteInfo> siteInfos = new ArrayList<>();
}
