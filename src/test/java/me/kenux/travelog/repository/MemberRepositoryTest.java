package me.kenux.travelog.repository;

import me.kenux.travelog.domain.Member;
import me.kenux.travelog.domain.UserPassword;
import me.kenux.travelog.domain.enums.MemberStatus;
import me.kenux.travelog.global.config.QueryDslConfig;
import me.kenux.travelog.repository.base.RepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryTest extends RepositoryTest {

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
