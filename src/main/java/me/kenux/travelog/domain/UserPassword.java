package me.kenux.travelog.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public UserPassword(String password, Member member) {
        this.password = password;
        this.lastChangedDate = OffsetDateTime.now();
        this.member = member;
    }

    public void changePassword(String password) {
        this.password = password;
        lastChangedDate = OffsetDateTime.now();
    }
}
