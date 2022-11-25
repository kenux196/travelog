package me.kenux.travelog.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kenux.travelog.domain.enums.MemberStatus;

import javax.persistence.*;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private MemberStatus status = MemberStatus.NORMAL;

    public Member(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void leave() {
        status = MemberStatus.LEAVED;
    }

    public void block() {
        status = MemberStatus.BLOCKED;
    }

    public static Member createNewMember(String name, String email) {
        return new Member(name, email);
    }
}
