package me.kenux.travelog.service;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.Member;
import me.kenux.travelog.domain.UserPassword;
import me.kenux.travelog.global.exception.CustomException;
import me.kenux.travelog.global.exception.ErrorCode;
import me.kenux.travelog.repository.MemberRepository;
import me.kenux.travelog.repository.PasswordRepository;
import me.kenux.travelog.service.dto.request.MemberJoinRequest;
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
    public void joinMember(MemberJoinRequest joinRequest) {
        if (memberRepository.existsByEmail(joinRequest.getEmail())) {
            throw new CustomException(ErrorCode.EMAIL_DUPLICATION);
        }
        final Member savedMember = memberRepository.save(joinRequest.toEntity());
        final UserPassword userPassword = new UserPassword(joinRequest.getPassword(), savedMember);
        passwordRepository.save(userPassword);
    }

    public List<Member> getMembers() {
        return memberRepository.findAll();
    }
}
