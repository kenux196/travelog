package me.kenux.travelog.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import me.kenux.travelog.domain.common.BaseTimeEntity;
import me.kenux.travelog.domain.member.entity.enums.MemberStatus;
import me.kenux.travelog.domain.member.entity.enums.UserRole;

import java.time.OffsetDateTime;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    private MemberStatus status = MemberStatus.NORMAL;

    private OffsetDateTime lastAccessDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "password_id", nullable = false)
    private UserPassword userPassword;

    @Builder
    public Member(@NonNull String name, @NonNull String email, @NonNull UserPassword password, @NonNull UserRole userRole) {
        this.name = name;
        this.email = email;
        this.userPassword = password;
        this.userRole = userRole;
    }

    public void doBlock() {
        status = MemberStatus.BLOCKED;
    }

    public void successLogin() {
        this.lastAccessDate = OffsetDateTime.now();
    }

    public static Member createNewMember(String name, String email, UserPassword password) {
        return new Member(name, email, password, UserRole.USER);
    }

    public static Member createAdmin(String name, String email, UserPassword password) {
        return new Member(name, email, password, UserRole.ADMIN);
    }

    public String getPassword() {
        return userPassword.getPassword();
    }
}
