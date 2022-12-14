package me.kenux.travelog.domain;

import lombok.*;
import me.kenux.travelog.domain.enums.MemberStatus;
import me.kenux.travelog.domain.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseTimeEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    private MemberStatus status = MemberStatus.NORMAL;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "password_id")
    private UserPassword password;

    @Builder
    public Member(String name, String email, UserPassword password, UserRole userRole) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    public void leave() {
        status = MemberStatus.LEAVED;
    }

    public void block() {
        status = MemberStatus.BLOCKED;
    }

    public static Member createNewMember(String name, String email, UserPassword password) {
        return new Member(name, email, password, UserRole.USER);
    }

    public static Member createAdmin(String name, String email, UserPassword password) {
        return new Member(name, email, password, UserRole.ADMIN);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 계정의 권한 목록을 리턴
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(userRole.getValue()));
        return roles;
    }

    @Override
    public String getPassword() {
        return password.getPassword();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
