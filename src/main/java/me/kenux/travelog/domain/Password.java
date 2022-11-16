package me.kenux.travelog.domain;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "password")
public class Password {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    private OffsetDateTime lastChangedDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
