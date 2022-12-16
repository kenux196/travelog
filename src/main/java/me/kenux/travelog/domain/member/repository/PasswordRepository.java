package me.kenux.travelog.domain.member.repository;

import me.kenux.travelog.domain.member.entity.UserPassword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepository extends JpaRepository<UserPassword, Long> {
}
