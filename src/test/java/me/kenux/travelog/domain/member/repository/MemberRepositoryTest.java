package me.kenux.travelog.domain.member.repository;

import jakarta.persistence.EntityManager;
import me.kenux.travelog.RepositoryTestConfigure;
import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.entity.UserPassword;
import me.kenux.travelog.domain.member.entity.enums.MemberStatus;
import me.kenux.travelog.domain.member.repository.dto.MemberSearchCond;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemberRepositoryTest extends RepositoryTestConfigure {

    @Autowired
    EntityManager em;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordRepository passwordRepository;

    @Test
    void save() {
        // given
        final Member newMember = Member.createNewMember("member1", "member1@email.com");

        // when
        memberRepository.save(newMember);
        em.clear();

        // then
        final Optional<Member> findMember = memberRepository.findById(newMember.getId());
        assertThat(findMember).isPresent();
    }

    @Test
    @DisplayName("회원 조건 검색: 이메일")
    void findByEmail() {
        // given
        String email = "member1@email.com";
        String name = "member1";
        final Member newMember = Member.createNewMember(name, email);
        memberRepository.save(newMember);

        // when
        MemberSearchCond cond = new MemberSearchCond();
        cond.setEmail(email);
        final List<Member> members = memberRepository.findMemberByCondition(cond);

        // then
        assertThat(members).hasSize(1);
    }

    @Test
    @DisplayName("회원 조건 검색: 이름")
    void findByName() {
        // given
        String email = "member1@email.com";
        String name = "member1";
        final Member newMember = Member.createNewMember(name, email);
        memberRepository.save(newMember);

        // when
        MemberSearchCond cond = new MemberSearchCond();
        cond.setName(name);
        final List<Member> members = memberRepository.findMemberByCondition(cond);

        // then
        assertThat(members).hasSize(1);
    }

    @Test
    @DisplayName("회원 조건 검색: 상태")
    void findByStatus() {
        // given
        String email = "member1@email.com";
        String name = "member1";
        final Member newMember = Member.createNewMember(name, email);
        newMember.doBlock();
        memberRepository.save(newMember);

        // when
        MemberSearchCond cond = new MemberSearchCond();
        cond.setStatus(MemberStatus.BLOCKED);
        final List<Member> members = memberRepository.findMemberByCondition(cond);

        // then
        assertThat(members).hasSize(1);
    }

    @Test
    void existByEmail() {
        // given
        final Member newMember = Member.createNewMember("member1", "member1@email.com");
        memberRepository.save(newMember);
        em.clear();

        // when
        final boolean result = memberRepository.existsByEmail("member1@email.com");

        // then
        assertThat(result).isTrue();
    }

    @Test
    void updateMemberTest() {
        // given
        String email = "member1@email.com";
        String name = "member1";
        final Member newMember = Member.createNewMember(name, email);
        newMember.doBlock();
        memberRepository.save(newMember);

        newMember.doBlock();
        em.flush();
        em.clear();
    }
}
