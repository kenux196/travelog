package me.kenux.travelog.domain.member.service;

import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.entity.enums.MemberStatus;
import me.kenux.travelog.domain.member.entity.enums.UserRole;
import me.kenux.travelog.domain.member.repository.MemberRepository;
import me.kenux.travelog.domain.member.repository.PasswordRepository;
import me.kenux.travelog.domain.member.repository.dto.MemberSearchCond;
import me.kenux.travelog.domain.member.service.dto.response.MemberInfo;
import me.kenux.travelog.domain.member.service.dto.response.MyInfo;
import me.kenux.travelog.global.exception.CustomException;
import me.kenux.travelog.global.exception.ErrorCode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    MemberRepository memberRepository;
    @Mock
    PasswordRepository passwordRepository;

    @InjectMocks
    MemberService memberService;

    private static Authentication authentication;
    private static SecurityContext securityContext;

    @BeforeAll
    static void setup() {
        authentication = Mockito.mock(Authentication.class);
        securityContext = getMockSecurityContext();
    }

    private static SecurityContext getMockSecurityContext() {
        securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        return securityContext;
    }

    @Test
    @DisplayName("회원 전체 조회 - 성공")
    void getMembers_success() {
        // given
        MemberSearchCond searchCond = Mockito.mock(MemberSearchCond.class);
        Member member = Mockito.mock(Member.class);
        List<Member> members = Collections.singletonList(member);
        given(memberRepository.findMemberByCondition(any())).willReturn(members);
        given(member.getStatus()).willReturn(MemberStatus.NORMAL);
        given(member.getUserRole()).willReturn(UserRole.USER);

        // when
        final List<MemberInfo.DetailResponse> result = memberService.getMembers(searchCond);

        // then
        assertThat(result).hasSize(1);
        verify(memberRepository, times(1)).findMemberByCondition(searchCond);
    }

    @Test
    @DisplayName("회원이 탈퇴하면 회원의 개인정보는 삭제되어야 한다.")
    void leave_member_test() {
        // given
        Member member = Mockito.mock(Member.class);
        given(memberRepository.findById(any())).willReturn(Optional.of(member));

        // when
        memberService.removeMember(any());

        // then
        then(memberRepository).should(times(1)).delete(any());
        then(passwordRepository).should(times(1)).delete(any());
    }

    @Test
    @DisplayName("회원상태 정보를 블럭 상태로 변경한다.")
    void change_member_block() {
        // given
        Member member = Mockito.mock(Member.class);
        given(memberRepository.findById(any())).willReturn(Optional.of(member));

        // when
        memberService.blockMember(any());

        // then
        verify(member, times(1)).doBlock();
    }

    @Test
    @DisplayName("회원 상세 조회 - 성공")
    void getMemberDetail_success() {
        // given
        Member member = Mockito.mock(Member.class);
        given(memberRepository.findById(any())).willReturn(Optional.of(member));
        given(member.getStatus()).willReturn(MemberStatus.NORMAL);
        given(member.getUserRole()).willReturn(UserRole.USER);

        // when
        final MemberInfo.DetailResponse memberDetail = memberService.getMemberDetail(any());

        // then
        assertThat(memberDetail).isNotNull();
        assertThat(memberDetail.name()).isEqualTo(member.getName());
    }

    @Test
    @DisplayName("회원 정보 simple - 성공")
    void getMemberSimple_success() {
        // given
        Member member = Mockito.mock(Member.class);
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));

        // when
        final MemberInfo.SimpleResponse memberSimpleInfo = memberService.getMemberSimpleInfo(anyLong());

        // then
        assertThat(memberSimpleInfo).isNotNull();
        assertThat(memberSimpleInfo.name()).isEqualTo(member.getName());
    }

    @Test
    @DisplayName("회원 상세 조회 - 실패 : Member not exist")
    void getMemberDetail_MemberNotExist() {
        // given
        given(memberRepository.findById(anyLong())).willReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> memberService.getMemberDetail(anyLong()));

        // then
        assertThat(throwable)
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.MEMBER_NOT_EXIST.getMessage());
    }

    @Test
    @DisplayName("로그인된 회원이 아닌 다른 회원정보가 조회시 예외발생.")
    void getMySimpleInfo_failed() {
        // given
        final Member member = Member.builder()
                .name("user")
                .email("user@test.com")
                .build();
        given(securityContext.getAuthentication()).willReturn(authentication);
        given(SecurityContextHolder.getContext().getAuthentication().getName()).willReturn("another@test.com");
        given(memberRepository.findById(any())).willReturn(Optional.of(member));

        // when
        final Throwable throwable = catchThrowable(() -> memberService.getMySimpleInfo(any()));

        // then
        assertThat(throwable)
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.MEMBER_WRONG_REQUEST.getMessage());
    }

    @Test
    @DisplayName("로그인된 회원 자신의 정보 요청시 성공")
    void getMySimpleInfo_success() {
        // given
        final Member member = Member.builder()
                .name("user")
                .email("user@test.com")
                .build();
        given(securityContext.getAuthentication()).willReturn(authentication);
        given(SecurityContextHolder.getContext().getAuthentication().getName()).willReturn(member.getEmail());
        given(memberRepository.findById(any())).willReturn(Optional.of(member));

        // when
        final MyInfo.Simple mySimpleInfo = memberService.getMySimpleInfo(any());

        // then
        assertThat(mySimpleInfo.name()).isEqualTo(member.getName());
    }
}
