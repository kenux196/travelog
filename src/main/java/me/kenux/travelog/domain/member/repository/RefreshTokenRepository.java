package me.kenux.travelog.domain.member.repository;

import me.kenux.travelog.domain.member.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    Optional<RefreshTokenEntity> findByMemberId(Long memberId);

    Optional<RefreshTokenEntity> findByToken(String token);

    @Query(value = "delete from RefreshTokenEntity r where r.member.id = :memberId")
    void deleteByMember(@Param("memberId") Long memberId);
}
