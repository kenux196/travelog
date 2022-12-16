package me.kenux.travelog.global.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.entity.UserPassword;
import me.kenux.travelog.domain.member.repository.MemberRepository;
import me.kenux.travelog.domain.member.repository.PasswordRepository;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Profile("test")
@Slf4j
public class TestInitService implements ApplicationListener<ApplicationStartedEvent> {

    private final MemberRepository memberRepository;
    private final PasswordRepository passwordRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationStartedEvent event) {
        log.info("로컬 개발 환경 : 멤버 추가 ");
        insertAdmin();
        insertMember();
    }

    private void insertAdmin() {
        final String encodedPassword = passwordEncoder.encode("1");
        UserPassword password = new UserPassword(encodedPassword);
        passwordRepository.save(password);
        final Member admin = Member.createAdmin("관리자", "admin@test.com", password);
        memberRepository.save(admin);
    }

    private void insertMember() {
        final String encodedPassword = passwordEncoder.encode("1");
        UserPassword password = new UserPassword(encodedPassword);
        passwordRepository.save(password);
        final Member admin = Member.createNewMember("사용자", "user@test.com", password);
        memberRepository.save(admin);
    }
}
