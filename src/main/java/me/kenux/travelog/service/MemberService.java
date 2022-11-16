package me.kenux.travelog.service;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.Member;
import me.kenux.travelog.domain.UserPassword;
import me.kenux.travelog.repository.MemberRepository;
import me.kenux.travelog.repository.PasswordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordRepository passwordRepository;

    @Transactional
    public void joinMember(Member member, String password) {
        final Member save = memberRepository.save(member);
        final UserPassword userPassword = new UserPassword(password, save);
        passwordRepository.save(userPassword);
    }

    public List<Member> getMembers() {
        return memberRepository.findAll();
    }
}
