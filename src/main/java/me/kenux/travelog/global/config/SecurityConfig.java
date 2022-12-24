package me.kenux.travelog.global.config;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.global.security.CustomAuthenticationProvider;
import me.kenux.travelog.global.security.CustomLoginSuccessHandler;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Override
    public void configure(WebSecurity web) {
        web
            .ignoring() // spring security 필터 타지 않도록 설정
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()) // 정적 리소스에 대한 필터 무시
            .antMatchers("/h2-console/**")
            .antMatchers("/h2/**"); // h2-console 무시
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//            .csrf().disable();
        http
            .authorizeRequests()
            .antMatchers("/", "/join", "/login", "/test").permitAll()
            .antMatchers("/admin/join", "/admin/login").permitAll()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated();
        http
            .formLogin()
            .loginPage("/admin/login")
            .successHandler(customLoginSuccessHandler());
        http
            .logout()
            .logoutUrl("/admin/logout")
            .logoutSuccessUrl("/admin")
            .deleteCookies("JSESSIONID")
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//            .withUser("kenux")
//            .password("{bcrypt}$2a$10$wPqb10jDmQakOP0kaw/tS.y5261/E/RwIVOY8vaesPiWFXCqLKn5K")
//            .roles("ADMIN", "USER");
        // 커스텀한 AuthenticationProvider 를 AuthenticationManager에 등록
        auth.authenticationProvider(customAuthenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler customLoginSuccessHandler() {
        // 로그인 성공 시 실행되는 CustomLoginSuccessHandler 를 빈으로 등록
        return new CustomLoginSuccessHandler();
    }

    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        // 실제 인증 담당 객체를 빈으로 등록
        return new CustomAuthenticationProvider(userDetailsService, passwordEncoder());
    }
}
