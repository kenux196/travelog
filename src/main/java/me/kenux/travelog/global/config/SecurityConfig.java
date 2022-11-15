package me.kenux.travelog.global.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) {
        web
            .ignoring() // spring security 필터 타지 않도록 설정
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()) // 정적 리소스에 대한 필터 무시
            .antMatchers("/h2-console/**"); // h2-console 무시
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
            .authorizeRequests()
            .antMatchers("/**").permitAll()
            .antMatchers("/test").permitAll()
            .anyRequest().authenticated()
            .and()
//            .httpBasic()
            .formLogin()
//            .defaultSuccessUrl("/view/test")
//            .and()
//            .authorizeRequests()
//            .antMatchers("/hello").permitAll()
//            .antMatchers("/api/hello").permitAll()
//            .antMatchers("/api/login").authenticated()
//            .anyRequest().authenticated()
//            .and()
//            .csrf().disable();
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("kenux")
            .password("{noop}1234")
            .roles("USER");
    }
}
