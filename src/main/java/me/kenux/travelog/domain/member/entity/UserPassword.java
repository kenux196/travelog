package me.kenux.travelog.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.OffsetDateTime;

@Entity
@Table(name = "password")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String password;

    private OffsetDateTime lastChangedDate;


    public UserPassword(@NonNull String password) {
        this.password = password;
        this.lastChangedDate = OffsetDateTime.now();
    }

    public void changePassword(String password) {
        this.password = password;
        lastChangedDate = OffsetDateTime.now();
    }

    // TODO - Need password validate & policy  2023-04-01 sky
}
