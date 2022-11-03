package me.kenux.travelog.domain;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private OffsetDateTime createdTime;

    private OffsetDateTime updatedTime;
}
