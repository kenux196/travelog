package me.kenux.travelog.domain.member.repository;

import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.entity.UserPassword;
import me.kenux.travelog.domain.member.entity.enums.MemberStatus;
import me.kenux.travelog.base.BaseRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemberBaseRepositoryTest extends BaseRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void save() {
        // given
        final Member newMember = getMember();

        // when
        memberRepository.save(newMember);
        em.clear();

        // then
        final Optional<Member> findMember = memberRepository.findById(newMember.getId());
        assertThat(findMember).isPresent();
    }

    @Test
    void saved_status_value_test() {
        // given
        final Member newMember = getMember();
        newMember.leave();

        // when
        memberRepository.save(newMember);
        em.clear();

        // then
        final Optional<Member> findMember = memberRepository.findById(newMember.getId());
        assertThat(findMember).isPresent();
        assertThat(findMember.get().getStatus()).isEqualTo(MemberStatus.LEAVED);
    }

    private static Member getMember() {
        final UserPassword password = new UserPassword("password");
        return Member.createNewMember("member1", "member1@email.com", password);
    }
}
