package me.kenux.travelog.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kenux.travelog.domain.member.entity.Member;

@Entity
@Table(name = "refresh_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefreshTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token")
    private String token;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public RefreshTokenEntity(Member member) {
        this.member = member;
    }

    public void updateToken(String token) {
        this.token = token;
    }
}
