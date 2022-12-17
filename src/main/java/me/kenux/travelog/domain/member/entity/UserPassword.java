package me.kenux.travelog.domain.member.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Entity
@Table(name = "password")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    private OffsetDateTime lastChangedDate;


    public UserPassword(String password) {
        this.password = password;
        this.lastChangedDate = OffsetDateTime.now();
    }

    public void changePassword(String password) {
        this.password = password;
        lastChangedDate = OffsetDateTime.now();
    }
}
