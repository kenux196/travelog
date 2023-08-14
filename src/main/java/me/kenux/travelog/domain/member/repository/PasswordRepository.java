package me.kenux.travelog.domain.member.repository;

import jakarta.validation.constraints.NotNull;
import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.entity.UserPassword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordRepository extends JpaRepository<UserPassword, Long> {

    void deleteByMember(@NotNull Member member);

    Optional<UserPassword> findByMember(Member member);
}
