package me.kenux.travelog.domain.test;

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
@Profile("local")
@Slf4j
public class TestInitService implements ApplicationListener<ApplicationStartedEvent> {

    private final MemberRepository memberRepository;
    private final PasswordRepository passwordRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationStartedEvent event) {
        log.info("로컬 개발 환경 : 테스트 멤버 추가 ");
        insertAdmin();
        insertMember();
    }

    private void insertAdmin() {
        UserPassword password = new UserPassword(getEncodedPassword());
        final Member admin = Member.createAdmin("관리자", "admin@test.com", password);
        passwordRepository.save(password);
        memberRepository.save(admin);
    }

    private void insertMember() {
        for (int i = 0; i < 10; i++) {
            UserPassword password = new UserPassword(getEncodedPassword());
            passwordRepository.save(password);
            String username = "user" + 100 + i;
            String email = username + "@test.com";
            final Member admin = Member.createNewMember(username, email, password);
            memberRepository.save(admin);
        }
    }

    private String getEncodedPassword() {
        return passwordEncoder.encode("1");
    }
}
