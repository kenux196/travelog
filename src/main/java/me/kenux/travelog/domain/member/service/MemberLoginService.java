package me.kenux.travelog.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.global.security.jwt.TokenInfo;
import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.repository.MemberRepository;
import me.kenux.travelog.global.exception.CustomException;
import me.kenux.travelog.global.exception.ErrorCode;
import me.kenux.travelog.global.security.jwt.JwtTokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberLoginService {

    private final MemberRepository memberRepository;

    @Transactional
    public void loginSuccessProcess(Long memberId) {
        final Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        member.successLogin();
    }

    public TokenInfo login(Authentication authentication) {
        final Member member = (Member) authentication.getPrincipal();
        log.info("username={}, roles={} ", member.getUsername(), member.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return JwtTokenProvider.generateJwtToken(authentication);
    }
}
