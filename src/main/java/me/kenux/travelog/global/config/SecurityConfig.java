package me.kenux.travelog.global.config;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.global.exception.ExceptionHandlerFilter;
import me.kenux.travelog.global.security.jwt.*;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
//@RequiredArgsConstructor
public class SecurityConfig {

    //    private final JwtTokenIssuer jwtTokenIssuer;
//    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public SecurityConfig(AuthenticationManagerBuilder authenticationManagerBuilder,
                          JwtAuthenticationProvider jwtAuthenticationProvider,
                          JwtAuthenticationEntryPoint unauthorizedHandler,
                          JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.authenticationManagerBuilder.authenticationProvider(jwtAuthenticationProvider);
        this.unauthorizedHandler = unauthorizedHandler;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    @Profile("!local")
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .requestMatchers("/assets/**")
                .requestMatchers("/index.html");
    }

    @Bean
    @Profile("local")
    public WebSecurityCustomizer webSecurityCustomizerLocal() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .requestMatchers("/assets/**")
                .requestMatchers("/index.html")
                .requestMatchers(toH2Console());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/", "/api/signup", "/api/auth/login", "/api/auth/refreshToken").permitAll()
                        .requestMatchers("/api/books").permitAll()
                        .requestMatchers("/api/auth/logout").authenticated()
                        .requestMatchers("/api/members/me").authenticated()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/**").hasRole("USER")
                        .anyRequest().permitAll())
                .formLogin().disable()
                .httpBasic().disable()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.apply(new JwtSecurityConfig(authenticationManagerBuilder.getOrBuild()));
//            .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
//            .addFilterBefore(new ExceptionHandlerFilter(), JwtAuthenticationFilter.class);

        return http.build();
    }

//    @Bean
//    public JwtAuthenticationFilter authenticationJwtTokenFilter() {
//        return new JwtAuthenticationFilter(jwtTokenIssuer, userDetailsService);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
