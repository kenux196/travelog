package me.kenux.travelog.domain.member.repository;

import me.kenux.travelog.BaseRepositoryConfig;
import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.entity.UserPassword;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemberRepositoryTest extends BaseRepositoryConfig {

    @Autowired
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordRepository passwordRepository;

    @Test
    void save() {
        // given
        final UserPassword password = new UserPassword("password");
        passwordRepository.save(password);
        final Member newMember = Member.createNewMember("member1", "member1@email.com", password);;


        // when
        memberRepository.save(newMember);
        em.clear();

        // then
        final Optional<Member> findMember = memberRepository.findById(newMember.getId());
        assertThat(findMember).isPresent();
    }
}
