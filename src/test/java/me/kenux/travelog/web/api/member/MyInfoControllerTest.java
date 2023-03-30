package me.kenux.travelog.web.api.member;

import me.kenux.travelog.domain.member.service.MyInfoService;
import me.kenux.travelog.domain.member.service.dto.response.MyInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MyInfoController.class)
class MyInfoControllerTest {

    @MockBean
    MyInfoService myInfoService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/members/me - success")
    @WithMockUser
    void getMyName_success() throws Exception {
        // given
        given(myInfoService.getMyName()).willReturn(Mockito.mock(MyInfo.OnlyName.class));

        // when
        final ResultActions resultActions = mockMvc.perform(get("/api/members/me")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authentication", "accessToken"));

        //then
        resultActions.andExpect(status().isOk())
                .andDo(print());
    }
}