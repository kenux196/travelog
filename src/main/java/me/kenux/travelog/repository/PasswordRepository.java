package me.kenux.travelog.repository;

import me.kenux.travelog.domain.UserPassword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepository extends JpaRepository<UserPassword, Long> {
}
