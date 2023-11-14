package org.koreait.configs;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.koreait.models.member.LoginFailureHandler;
import org.koreait.models.member.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

@Configuration
@EnableConfigurationProperties(FileUploadConfig.class)
public class SecurityConfig {

    @Autowired
    private FileUploadConfig fileUploadConfig;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        /* 인증 설정 - 로그인 관련 S */

        // 로그인
        http.formLogin(f -> {
           f.loginPage("/member/login")
                   .usernameParameter("email")
                   .passwordParameter("password")
                   .successHandler(new LoginSuccessHandler())
                   .failureHandler(new LoginFailureHandler());

        }); // DSL

        // 로그아웃
        http.logout(c -> {
            c.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                    .logoutSuccessUrl("/member/login"); // 로그아웃 성공시 로그인 페이지로 이동
        });

        /* 인증 설정 - 로그인 관련 E */

        // iframe 관련 (설정해주어야 사용가능 기본적으로 보안문제로 인하여 막혀있음)
        http.headers(c -> {

            c.frameOptions(o -> o.sameOrigin());
        });

        /* 인가 설정 - 접근 통제 S */

        // authenticated(): 회원 전용 페이지(로그인한 회원만 접근 가능)
        http.authorizeHttpRequests(c -> {
            c.requestMatchers("/mypage/**").authenticated()
                    .requestMatchers("/admin/**").hasAuthority("ADMIN") // 관리자 권한만 접근 가능 (하단 설정 경로는 제외)
                    .anyRequest().permitAll();
                    // anyRequest().permitAll(): 나머지 페이지는 권한 필요 없이 접근가능
        });

        // 인증 실패시 유입
        http.exceptionHandling(c -> {
            c.authenticationEntryPoint((req, resq, e) -> {
                String URI = req.getRequestURI();
                if (URI.indexOf("/admin") != -1) { // 관리자 페이지 - 401 응답 코드

                    resq.sendError(HttpServletResponse.SC_UNAUTHORIZED, "NOT AUTHORIZED");
                    // 401

                /*} else if (URI.indexOf("/mypage") != -1) { // 403 응답 코드

                    resq.sendError(HttpServletResponse.SC_FORBIDDEN, "FORBIDDEN");*/

                } else { // 회원전용 페이지( 예) /mypage) -> 로그인 페이지 이동

                    String url = req.getContextPath() + "/member/login";
                    resq.sendRedirect(url);
                }
            });
        });

        /* 인가 설정 - 접근 통제 E */

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        // 시큐리티 설정이 적용될 필요가 없는 경로 설정

                    // ignoring(): 시큐리티 설정 무시
        return w -> w.ignoring().requestMatchers(
                "/front/css/**",
                            "/front/js/**",
                            "/front/images/**",

                            "/mobile/css/**",
                            "/mobile/js/**",
                            "/mobile/images/**",

                            "/admin/css/**",
                            "/admin/js/**",
                            "/admin/images/**",

                            "/common/css/**",
                            "/common/js/**",
                            "/common/images/**",
                            fileUploadConfig.getUrl() + "**");

    }

    @Bean
    public PasswordEncoder passwordEncoder() { // BCrypt형태로 패스워드 설정
        return new BCryptPasswordEncoder();
    }
}
