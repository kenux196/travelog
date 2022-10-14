package me.kenux.travelog.domain;

import lombok.*;
import org.hibernate.annotations.BatchSize;

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
    @BatchSize(size = 5)
    List<SiteInfo> siteInfos = new ArrayList<>();
}
