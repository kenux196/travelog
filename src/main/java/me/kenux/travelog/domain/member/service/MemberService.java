package me.kenux.travelog.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.repository.MemberRepository;
import me.kenux.travelog.domain.member.repository.PasswordRepository;
import me.kenux.travelog.domain.member.repository.dto.MemberSearchCond;
import me.kenux.travelog.domain.member.service.dto.response.MemberInfo;
import me.kenux.travelog.global.exception.CustomException;
import me.kenux.travelog.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordRepository passwordRepository;

    @Transactional
    public void removeMember(Long memberId) {
        log.info("Remove Member: {}", memberId);
        final Member member = getMember(memberId);
        memberRepository.delete(member);
        passwordRepository.delete(member.getUserPassword());
    }

    public List<MemberInfo.DetailResponse> getMembers(MemberSearchCond cond) {
        return memberRepository.findMemberByCondition(cond).stream()
            .map(MemberInfo.DetailResponse::of)
            .toList();
    }

    public MemberInfo.DetailResponse getMemberDetail(Long id) {
        return memberRepository.findById(id)
            .map(MemberInfo.DetailResponse::of)
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_EXIST));
    }

    public MemberInfo.SimpleResponse getMemberSimpleInfo(Long id) {
        return memberRepository.findById(id)
            .map(MemberInfo.SimpleResponse::of)
            .orElseThrow(() -> new CustomException(ErrorCode.AUTH_MEMBER_NOT_EXIST));
    }

    @Transactional
    public void blockMember(Long memberId) {
        final Member member = getMember(memberId);
        log.info("Block Member: {}", member.getId());
        member.doBlock();
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_EXIST));
    }
}
