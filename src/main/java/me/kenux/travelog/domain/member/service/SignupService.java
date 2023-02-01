package me.kenux.travelog.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.domain.member.service.dto.request.SignupRequest;
import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.entity.UserPassword;
import me.kenux.travelog.domain.member.repository.MemberRepository;
import me.kenux.travelog.domain.member.repository.PasswordRepository;
import me.kenux.travelog.global.exception.CustomException;
import me.kenux.travelog.global.exception.ErrorCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignupService {

    private final MemberRepository memberRepository;
    private final PasswordRepository passwordRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignupRequest joinRequest) {
        if (memberRepository.existsByEmail(joinRequest.getEmail())) {
            throw new CustomException(ErrorCode.MEMBER_ALREADY_EXIST);
        }

        final String encodedPassword = passwordEncoder.encode(joinRequest.getPassword());
        final UserPassword password = new UserPassword(encodedPassword);
        passwordRepository.save(password);

        final Member newMember = Member.createNewMember(joinRequest.getName(), joinRequest.getEmail(), password);
        memberRepository.save(newMember);
    }
}
