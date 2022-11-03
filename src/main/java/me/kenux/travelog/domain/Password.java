package me.kenux.travelog.domain;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "password")
public class Password {

    @Id
    private String memberName;

    private String password;

    private OffsetDateTime createdTime;

    private OffsetDateTime updatedTime;
}