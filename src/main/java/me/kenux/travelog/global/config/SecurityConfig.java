package me.kenux.travelog.global.config;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.global.security.CustomLoginSuccessHandler;
import me.kenux.travelog.global.security.jwt.JwtAuthenticationEntryPoint;
import me.kenux.travelog.global.security.jwt.JwtAuthenticationFilter;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    public JwtAuthenticationFilter authenticationJwtTokenFilter() {
        return new JwtAuthenticationFilter();
    }

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
        http
            .csrf().disable();
        http
            .authorizeRequests()
            .antMatchers("/", "/join", "/login", "/test").permitAll()
            .antMatchers("/api/login", "/api/join").permitAll()
            .antMatchers("/admin/join", "/admin/login").permitAll()
            .antMatchers("/admin/**", "/api/test/admin").hasRole("ADMIN")
            .antMatchers("/admin/**", "/api/test/user").hasRole("USER")
            .anyRequest().authenticated();
        http
            .formLogin().disable()
            .httpBasic().disable()
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
            .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//        http
//            .formLogin()
//            .loginPage("/admin/login")
//            .successHandler(customLoginSuccessHandler());
//        http
//            .logout()
//            .logoutUrl("/admin/logout")
//            .logoutSuccessUrl("/admin")
//            .deleteCookies("JSESSIONID")
        ;
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//            .withUser("kenux")
//            .password("{bcrypt}$2a$10$wPqb10jDmQakOP0kaw/tS.y5261/E/RwIVOY8vaesPiWFXCqLKn5K")
//            .roles("ADMIN", "USER");
        // 커스텀한 AuthenticationProvider 를 AuthenticationManager에 등록
//        auth.authenticationProvider(customAuthenticationProvider());
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler customLoginSuccessHandler() {
        // 로그인 성공 시 실행되는 CustomLoginSuccessHandler 를 빈으로 등록
        return new CustomLoginSuccessHandler();
    }

//    @Bean
//    public CustomAuthenticationProvider customAuthenticationProvider() {
//         실제 인증 담당 객체를 빈으로 등록
//        return new CustomAuthenticationProvider(userDetailsService, passwordEncoder());
//    }
}
