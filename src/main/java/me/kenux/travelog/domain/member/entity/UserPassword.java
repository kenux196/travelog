package me.kenux.travelog.domain.member.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "password")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class UserPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String password;

    private OffsetDateTime lastChangedDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public UserPassword(String password) {
        this.password = password;
        this.lastChangedDate = OffsetDateTime.now();
    }

    public UserPassword(@NotNull Member member, @NotNull String password) {
        this.member = member;
        this.password = password;
        this.lastChangedDate = OffsetDateTime.now();
    }

    public void changePassword(String password) {
        this.password = password;
        lastChangedDate = OffsetDateTime.now();
    }

    // TODO - Need password validate & policy  2023-04-01 sky
}
