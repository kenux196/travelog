package me.kenux.travelog.web.api;

import me.kenux.travelog.domain.member.entity.enums.UserRole;
import me.kenux.travelog.global.config.SecurityConfig;
import me.kenux.travelog.global.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = HelloRestController.class,
//        excludeAutoConfiguration = {
//                UserDetailsServiceAutoConfiguration.class, SecurityAutoConfiguration.class
//        }
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = SecurityConfig.class
                )}
)
class HelloRestControllerTest {

    private final String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkB0ZXN0LmNvbSIsImF1dGgiOiJST0xFX0FETUlOIiwiZXhwIjoxNjc1MDc5NjM1fQ.Dh7-mAaVLe6jS94AdnVKdwnzMuhvIPGBWZATeykc-_V7ib-sAwXCqy1MMBjWKIzontQpkPzfjI7MpmmJGn238A";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @WithMockUser(roles = "USER", username = "user@test.com")
    void getHello() throws Exception {
        // given
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken("test", "1234", authorities(UserRole.USER));
        given(jwtTokenProvider.getAuthentication(anyString())).willReturn(authentication);

        mockMvc.perform(
                        get("/hello")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    private Collection<? extends GrantedAuthority> authorities(UserRole userRole) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userRole.getValue()));
        return authorities;
    }

}