package me.kenux.travelog.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.entity.UserPassword;
import me.kenux.travelog.global.exception.CustomException;
import me.kenux.travelog.global.exception.ErrorCode;
import me.kenux.travelog.domain.member.repository.MemberRepository;
import me.kenux.travelog.domain.member.repository.PasswordRepository;
import me.kenux.travelog.domain.member.dto.request.MemberJoinRequest;
import me.kenux.travelog.domain.member.dto.response.MemberInfoResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordRepository passwordRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void joinMember(MemberJoinRequest joinRequest) {
        if (memberRepository.existsByEmail(joinRequest.getEmail())) {
            throw new CustomException(ErrorCode.EMAIL_DUPLICATION);
        }

        final String encodedPassword = passwordEncoder.encode(joinRequest.getPassword());
        final UserPassword password = new UserPassword(encodedPassword);
        passwordRepository.save(password);

        final Member newMember = Member.createNewMember(joinRequest.getName(), joinRequest.getEmail(), password);
        memberRepository.save(newMember);
    }

    public List<Member> getMembers() {
        return memberRepository.findAll();
    }

    @Transactional
    public void removeMember(Long memberId) {
        log.info("Remove Member: {}", memberId);
        final Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        passwordRepository.delete(member.getUserPassword());
        memberRepository.delete(member);
    }

    public List<MemberInfoResponse> getMemberInfoResponse() {
        return memberRepository.findAll().stream()
            .map(MemberInfoResponse::from)
            .collect(Collectors.toList());
    }

    public MemberInfoResponse getMemberDetail(Long id) {
        return memberRepository.findById(id)
            .map(MemberInfoResponse::from)
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }
}