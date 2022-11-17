package com.example.giftshop.common.config;

import com.example.giftshop.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final MemberService memberService;

    @Autowired
    public SecurityConfig(MemberService memberService) {
        this.memberService = memberService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.formLogin()
                .loginPage("/members/login")    //로그인 페이지
                .successHandler(successHandler())   //로그인 성공시 이동
                .usernameParameter("email")     //로그인시 파라미터 email로 설정
                .failureUrl("/members/login/error")     //로그인 실패시 이동
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) //로그아웃 페이지
                .logoutSuccessUrl("/"); //로그아웃 성공시 이동

        http.authorizeRequests() //페이지별 권한 설정
                .mvcMatchers("/", "/members/**","/goods/**","/images/**").permitAll() //메인, 회원, 상품, 이미지 관련 페이지 권한 x
                .mvcMatchers("/admin/**").hasRole("ADMIN") //관리자 페이지 권한 확인
                .anyRequest().authenticated();

        //인증되지 않은 사용자 접근
        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());

        return http.build();
    }
    @Bean
    public AuthenticationSuccessHandler successHandler() { //로그인 성공 시 이동 url 설정
        return new CustomLoginSuccessHandler("/"); //기본값
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
