package me.kenux.travelog.web.api.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kenux.travelog.domain.member.service.AuthService;
import me.kenux.travelog.domain.member.service.dto.request.LoginRequest;
import me.kenux.travelog.domain.member.service.dto.request.ReissueTokenRequest;
import me.kenux.travelog.global.config.SecurityConfig;
import me.kenux.travelog.global.exception.CustomException;
import me.kenux.travelog.global.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = AuthController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        })
class AuthControllerTest {

    public static final String BASE_URL = "/api/auth";

    @MockBean
    AuthService authService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @WithAnonymousUser
    void login_success() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("admin@test.com");
        loginRequest.setPassword("1");
        String requestBody = objectMapper.writeValueAsString(loginRequest);

        // when
        final ResultActions actions = mockMvc.perform(post(BASE_URL + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        );

        // then
        actions.andExpect(status().isOk())
                .andDo(print());
        verify(authService, times(1)).login(any());
    }

    @Test
    @WithAnonymousUser
    void login_failed_requestBody_emptyPassword() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("admin@test.com");
        loginRequest.setPassword("");
        String content = objectMapper.writeValueAsString(loginRequest);

        // when
        final ResultActions actions = mockMvc.perform(post(BASE_URL + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        );

        // then
        actions.andExpect(status().isBadRequest())
                .andDo(print());
        verify(authService, never()).login(any());
    }

    @Test
    @WithAnonymousUser
    void login_failed_requestBody_wrongPassword() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("admin@test.com");
        loginRequest.setPassword("wrong_password");
        String content = objectMapper.writeValueAsString(loginRequest);
        given(authService.login(any())).willThrow(new CustomException(ErrorCode.AUTH_WRONG_PASSWORD));

        // when
        final ResultActions actions = mockMvc.perform(post(BASE_URL + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        );

        // then
        actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.AUTH_WRONG_PASSWORD.getCode()))
                .andDo(print());
    }

    @Test
    @WithAnonymousUser
    void login_failed_requestBody_emptyUsername() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("");
        loginRequest.setPassword("1");
        String requestBody = objectMapper.writeValueAsString(loginRequest);

        // when
        final ResultActions actions = mockMvc.perform(post(BASE_URL + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        );

        // then
        actions.andExpect(status().isBadRequest())
                .andDo(print());
        verify(authService, never()).login(any());
    }

    @Test
    @WithMockUser
    void logout_success() throws Exception {

        // when
        final ResultActions actions = mockMvc.perform(post(BASE_URL + "/logout"));

        // then
        actions.andExpect(status().isOk())
                .andDo(print());
        verify(authService, times(1)).logout(any());
    }

    @Test
    @WithMockUser
    void refreshToken_success() throws Exception {
        // given
        ReissueTokenRequest reissueTokenRequest = new ReissueTokenRequest();
        reissueTokenRequest.setToken("valid_token");
        final String content = objectMapper.writeValueAsString(reissueTokenRequest);

        // when
        final ResultActions actions = mockMvc.perform(
                post(BASE_URL + "/refreshToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        // then
        actions.andExpect(status().isOk())
                .andDo(print());
        verify(authService, times(1)).reissueAccessToken(any());
    }

    @Test
//    @WithMockUser
    void refreshToken_failed_notExist_token() throws Exception {
        // given
        ReissueTokenRequest reissueTokenRequest = new ReissueTokenRequest();
        reissueTokenRequest.setToken("");
        final String content = objectMapper.writeValueAsString(reissueTokenRequest);

        // when
        final ResultActions actions = mockMvc.perform(
                post(BASE_URL + "/refreshToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        // then
        actions.andExpect(status().isBadRequest())
                .andDo(print());
        verify(authService, never()).reissueAccessToken(any());
    }
}