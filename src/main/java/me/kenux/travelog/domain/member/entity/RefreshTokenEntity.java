package me.kenux.travelog.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private String email;

    public RefreshTokenEntity(String token, String email) {
        this.token = token;
        this.email = email;
    }

    public void updateToken(String token) {
        this.token = token;
    }
}
