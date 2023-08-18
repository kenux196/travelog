package me.kenux.travelog.global.config;

import me.kenux.travelog.global.security.jwt.JwtAccessDeniedHandler;
import me.kenux.travelog.global.security.jwt.JwtAuthenticationEntryPoint;
import me.kenux.travelog.global.security.jwt.JwtAuthenticationProvider;
import me.kenux.travelog.global.security.jwt.JwtSecurityConfig;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
                .requestMatchers(new AntPathRequestMatcher("/assets/**"))
                .requestMatchers(new AntPathRequestMatcher("/index.html"));
    }

    @Bean
    @Profile("local")
    public WebSecurityCustomizer webSecurityCustomizerLocal() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .requestMatchers(new AntPathRequestMatcher("/assets/**"))
                .requestMatchers(new AntPathRequestMatcher("/index.html"))
                .requestMatchers(toH2Console());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                new AntPathRequestMatcher("/"),
                                new AntPathRequestMatcher("/api/signup"),
                                new AntPathRequestMatcher("/api/auth/login"),
                                new AntPathRequestMatcher("/api/auth/refreshToken")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/auth/logout")).authenticated()
                        .requestMatchers(new AntPathRequestMatcher("/api/members/me")).authenticated()
                        .requestMatchers(new AntPathRequestMatcher("/api/admin/**")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/api/books")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/**")).hasRole("USER")
                        .anyRequest().permitAll())
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(config -> config.authenticationEntryPoint(unauthorizedHandler)
                        .accessDeniedHandler(jwtAccessDeniedHandler))
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

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
