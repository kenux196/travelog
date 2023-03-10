package me.kenux.travelog.web.api.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kenux.travelog.domain.member.service.SignupService;
import me.kenux.travelog.domain.member.service.dto.request.SignupRequest;
import me.kenux.travelog.global.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest(classes = SignupController.class)
//@AutoConfigureMockMvc
@WebMvcTest(
        controllers = SignupController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        })
class SignupControllerTest {
    @MockBean
    private SignupService signupService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithAnonymousUser
    void signup_success() throws Exception {
        // given
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setName("user");
        signupRequest.setEmail("user@test.com");
        signupRequest.setPassword("1234");
        final String content = objectMapper.writeValueAsString(signupRequest);

        final ResultActions actions = mockMvc.perform(
                post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .param("name", "user")
                        .param("email", "user@test.com")
                        .param("password", "1111")
                        .with(csrf()));

        // then
        actions.andExpect(status().isNoContent());
    }

}