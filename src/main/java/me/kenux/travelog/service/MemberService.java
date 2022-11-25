package me.kenux.travelog.service;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.Member;
import me.kenux.travelog.domain.UserPassword;
import me.kenux.travelog.global.exception.CustomException;
import me.kenux.travelog.global.exception.ErrorCode;
import me.kenux.travelog.repository.MemberRepository;
import me.kenux.travelog.repository.PasswordRepository;
import me.kenux.travelog.service.dto.request.MemberJoinRequest;
import me.kenux.travelog.service.dto.response.MemberInfoResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        final Member newMember = Member.createNewMember(joinRequest.getName(), joinRequest.getEmail());
        final Member savedMember = memberRepository.save(newMember);
        final UserPassword userPassword = new UserPassword(joinRequest.getPassword(), savedMember);
        passwordRepository.save(userPassword);
    }

    public List<Member> getMembers() {
        return memberRepository.findAll();
    }

    @Transactional
    public void leaveMember(Long memberId) {
        final Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        member.leave();
    }


    // TODO - 아래 코드는 테스트를 위한 코드이므로 언젠가는 제거해야 한다. 2022-11-24 skyun
    public List<MemberInfoResponse> getMemberInfoResponse() {
        List<MemberInfoResponse> memberInfoResponses = new ArrayList<>();
        for (int i = 0; i < 23; i++) {
            final long id = (long) i + 1;
            final String name = "member_" + id;
            final String email = name + "@email.com";
            final LocalDateTime joinDateBase = LocalDateTime.of(2022, 01, 01, 10, 10, 10);
            final MemberInfoResponse memberInfoResponse = new MemberInfoResponse();
            memberInfoResponse.setId(id);
            memberInfoResponse.setName(name);
            memberInfoResponse.setEmail(email);
            memberInfoResponse.setJoinDate(joinDateBase.plusDays(i));
            memberInfoResponse.setStatus("normal");
            memberInfoResponses.add(memberInfoResponse);
        }
        return memberInfoResponses;
    }
}
