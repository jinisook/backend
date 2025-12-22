package com.example.club.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.club.handler.LoginSuccessHandler;

import lombok.extern.log4j.Log4j2;

@EnableWebSecurity // 모든 웹 요청에 대해 Security Filter Chain 적용
@Log4j2
@Configuration // -> 환경설정을 하는 클래스 의미를 부여(스프링 설정 클래스)
// 시큐리티 설정 클래스
public class SercurityConfig {

    @Bean // == 객체 생성
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // http.authorizeHttpRequests(authorize ->
        // authorize.anyRequest().authenticated())
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/assets/**", "/member/auth", "/img/**").permitAll()
                .requestMatchers("/member/**").hasRole("USER")
                .requestMatchers("/manager/**").hasAnyRole("MANAGER") // .hasAnyRole -> role를 여러개 담을 수 있고 or도 가능
                .requestMatchers("/admin/**").hasAnyRole("ADMIN"))
                // .httpBasic(Customizer.withDefaults()); // httpBasic - 로그인 미니 창 띄움
                // .formLogin(Customizer.withDefaults()); // 기본 로그인창 보여줌(내가 생성안한)
                .formLogin(login -> login
                        .loginPage("/member/login").permitAll()
                        // .defaultSuccessUrl("/", true))
                        .successHandler(loginSuccessHandler()))
                .oauth2Login(login -> login.successHandler(loginSuccessHandler())) // 소셜 로그인 가능 설정
                .logout(logout -> logout
                        .logoutUrl("/member/logout") // 로그아웃 post로 처리
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"));

        return http.build();
    }

    @Bean
    LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        // 운영, 실무, 여러 암호화 알고리즘 사용 {bcrypt}
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();

        // 연습, 단일 알고리즘 사용
        // return new BCryptPasswordEncoder();
    }

    // 임시 User 생성 - InMemoryUserDetailsManager

}
