package me.kenux.travelog.domain.member.service;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.repository.MemberRepository;
import me.kenux.travelog.domain.member.service.dto.response.MyInfo;
import me.kenux.travelog.global.exception.CustomException;
import me.kenux.travelog.global.exception.ErrorCode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyInfoService {

    private final MemberRepository memberRepository;

    public MyInfo.OnlyName getMyName() {
        return MyInfo.OnlyName.of(
                getMember(getUserNameFromSecurityContext()));
    }

    private Member getMember(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_EXIST));
    }

    private String getUserNameFromSecurityContext() {
        return  SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
