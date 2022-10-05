package me.kenux.travelog.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "camp")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Camp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address1;

    private String address2;
}
