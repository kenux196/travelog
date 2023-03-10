package me.kenux.travelog.web.api;

import me.kenux.travelog.global.security.jwt.JwtTokenIssuer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(controllers = HelloRestController.class,
//        excludeFilters = {
//                @ComponentScan.Filter(
//                        type = FilterType.ASSIGNABLE_TYPE,
//                        classes = { SecurityConfig.class, WebConfig.class }
//                )},
//        excludeAutoConfiguration = {
//            UserDetailsServiceAutoConfiguration.class, SecurityAutoConfiguration.class
//        }
//)
@WebMvcTest(HelloRestController.class)
class HelloRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JwtTokenIssuer jwtTokenIssuer;
    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    @DisplayName("GET /hello: 인증된 사용자 200 응답")
    @WithMockUser(roles = "USER", username = "user@test.com")
    void getHello() throws Exception {
        mockMvc.perform(get("/hello")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("OK~~~"))
                .andDo(print());
    }

    @Test
    @DisplayName("GET /hello : 미인증인 경우, 401 응답")
    @WithAnonymousUser
    void getHello_unAuthorized() throws Exception {
        // when
        final RequestBuilder requestBuilder = get("/hello").contentType(MediaType.APPLICATION_JSON);
        final ResultActions resultActions = mockMvc.perform(requestBuilder);

        // then
        resultActions
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @DisplayName("POST /hello 인증된 사용자 200 응답")
    @WithMockUser(roles = "ADMIN", username = "admin@test.com")
    void postHello() throws Exception {
        mockMvc.perform(post("/hello")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())) // csrf 해결.
                .andExpect(status().isCreated())
                .andDo(print());
    }
}