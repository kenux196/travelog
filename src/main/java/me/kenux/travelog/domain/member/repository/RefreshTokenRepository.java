package me.kenux.travelog.domain.member.repository;

import me.kenux.travelog.domain.member.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    Optional<RefreshTokenEntity> findByMemberId(Long memberId);

    Optional<RefreshTokenEntity> findByToken(String token);
}
