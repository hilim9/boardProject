package org.koreait.configs;

import org.koreait.models.member.LoginFailureHandler;
import org.koreait.models.member.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.formLogin(f -> {
           f.loginPage("/member/login")
                   .usernameParameter("email")
                   .passwordParameter("password")
                   .successHandler(new LoginSuccessHandler())
                   .failureHandler(new LoginFailureHandler());

        }); // DSL

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() { // BCrypt형태로 패스워드 설정
        return new BCryptPasswordEncoder();
    }
}
