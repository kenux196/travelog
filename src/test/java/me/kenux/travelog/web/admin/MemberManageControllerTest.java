package me.kenux.travelog.web.admin;

import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.entity.UserPassword;
import me.kenux.travelog.domain.member.entity.enums.UserRole;
import me.kenux.travelog.domain.member.service.MemberService;
import me.kenux.travelog.domain.member.service.dto.response.MemberInfo;
import me.kenux.travelog.global.exception.CustomException;
import me.kenux.travelog.global.exception.ErrorCode;
import me.kenux.travelog.global.security.jwt.JwtTokenIssuer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MemberManageController.class)
class MemberManageControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JwtTokenIssuer jwtTokenIssuer;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private MemberService memberService;

    private final String BASE_URI = "/api/admin/members";


    @BeforeEach
    void beforeEach() {
        // given
        given(memberService.getMembers(any())).willReturn(getMembersTestData());
    }

    private List<MemberInfo.DetailResponse> getMembersTestData() {
        List<Member> memberList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String name = "user" + i;
            String email = name + "@test.com";
            final Member member = Member.builder()
                    .name(name)
                    .email(email)
                    .userRole(i == 0 ? UserRole.ADMIN : UserRole.USER)
                    .build();
            memberList.add(member);
        }
        return memberList.stream()
                .map(MemberInfo.DetailResponse::of)
                .toList();
    }

    @Test
    @DisplayName("회원 리스트 조회 - admin 성공")
    @WithMockUser(roles = "ADMIN", username = "admin@test.com")
    void getMembers_admin_success() throws Exception {
        // when
        final RequestBuilder requestBuilder = get(BASE_URI);
        final ResultActions resultActions = mockMvc.perform(requestBuilder);

        // then
        resultActions
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("회원 리스트 조회 - 미인증 회원 접근 401 error")
    @WithAnonymousUser
    void getMembers_user_failed() throws Exception {
        // when
        final RequestBuilder requestBuilder = get(BASE_URI);
        final ResultActions resultActions = mockMvc.perform(requestBuilder);

        // then
        resultActions
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @DisplayName("회원 정보 상세 조회 성공")
    @WithMockUser
    void getMemberDetail_success() throws Exception {
        // when then
        mockMvc.perform(get(BASE_URI + "/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("회원 정보 상세 조회 실패 - 회원 정보 없음")
    @WithMockUser
    void getMemberDetail_failed() throws Exception {
        // given
        given(memberService.getMemberDetail(anyLong())).willThrow(new CustomException(ErrorCode.MEMBER_NOT_EXIST));
        // when then
        mockMvc.perform(get(BASE_URI + "/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("M003"))
                .andDo(print());
    }

    @Test
    @DisplayName("회원 삭제 성공")
    @WithMockUser
    void deleteMember_success() throws Exception {
        mockMvc.perform(delete(BASE_URI + "/1").with(csrf()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("회원 블럭킹 성공")
    @WithMockUser
    void blockingMember_success() throws Exception {
        mockMvc.perform(patch(BASE_URI + "/1/block").with(csrf()))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
