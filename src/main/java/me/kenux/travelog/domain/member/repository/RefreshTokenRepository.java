package me.kenux.travelog.domain.member.repository;

import me.kenux.travelog.domain.member.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    Optional<RefreshTokenEntity> findByToken(String token);

    Optional<RefreshTokenEntity> findByEmail(String username);

    boolean existsByToken(String token);

    void deleteByEmail(String email);
}
