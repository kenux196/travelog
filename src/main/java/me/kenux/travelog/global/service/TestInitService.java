package me.kenux.travelog.global.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.domain.Member;
import me.kenux.travelog.domain.UserPassword;
import me.kenux.travelog.repository.MemberRepository;
import me.kenux.travelog.repository.PasswordRepository;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Profile("test")
@Slf4j
public class TestInitService implements ApplicationListener<ApplicationStartedEvent> {

    private final MemberRepository memberRepository;
    private final PasswordRepository passwordRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        log.info("로컬 개발 환경 : 관리자 추가 ");

        final String encodedPassword = passwordEncoder.encode("1111");
        UserPassword password = new UserPassword(encodedPassword);
        passwordRepository.save(password);
        final Member admin = Member.createAdmin("관리자", "admin@travlog.com", password);
        memberRepository.save(admin);
    }
}
