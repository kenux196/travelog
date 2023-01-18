package me.kenux.travelog.domain.member.repository;

import me.kenux.travelog.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {

    Optional<Member> findByEmail(String email);
    Optional<Member> findMemberByEmail(String email);

    boolean existsByEmail(String email);
}
