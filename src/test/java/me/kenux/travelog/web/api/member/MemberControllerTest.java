package me.kenux.travelog.web.api.member;

import me.kenux.travelog.domain.member.service.MemberService;
import me.kenux.travelog.domain.member.service.dto.response.MemberInfo;
import me.kenux.travelog.global.security.jwt.JwtTokenIssuer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @MockBean
    private MemberService memberService;
    @MockBean
    private JwtTokenIssuer jwtTokenIssuer;
    @MockBean
    private UserDetailsService userDetailsService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("사용자 개인 정보 가져오기")
    @WithMockUser
    void getMyInfo_success() throws Exception {
        //given
        MemberInfo.SimpleResponse simpleResponse =
                new MemberInfo.SimpleResponse(1L, "user", "user@test.com", OffsetDateTime.now());
        given(memberService.getMemberSimpleInfo(anyLong())).willReturn(simpleResponse);
        given(userDetailsService.loadUserByUsername(any())).willReturn(mock(UserDetails.class));
        willDoNothing().given(jwtTokenIssuer).validateToken(any());

        // when then
        mockMvc.perform(get("/api/members/1")
                        .header("Authorization", "Bearer aaa"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("사용자 개인 정보 가져오기 실패 - 권한 없음.")
    void getMyInfo_failed() throws Exception {
        mockMvc.perform(get("/api/members/1"))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }
}