package com.example.giftshop.member.controller;

import com.example.giftshop.member.dto.MemberFormDTO;
import com.example.giftshop.member.entity.Member;
import com.example.giftshop.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class MemberControllerTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createTestMember(String email, String password) {
        MemberFormDTO memberFormDTO = MemberFormDTO.builder()
                .email(email)
                .password(password)
                .name("테스터1")
                .address("테스트 주소")
                .build();

        Member member = Member.createMember(memberFormDTO, passwordEncoder);
        System.out.println("----------- 멤버 생성 테스트 : " + member.toString());
        return memberService.joinMember(member);
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void loginSuccessTest() throws Exception{
        String email = "test@email.com";
        String password = "123456789";
        Member member = this.createTestMember(email,password);

        System.out.println("로그인 성공 테스트 :" + member.toString());

        mockMvc.perform(
                formLogin()                                         // form 태그 기반의 로그인 인증 방식 테스트
                        .loginProcessingUrl("/members/login")       // 요청 url
                        .userParameter("email")      // 로그인시 파라미터 email로 설정
                        .user(email).password(password))            // 사용 email, password
                .andExpect(SecurityMockMvcResultMatchers.authenticated());
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    public void loginFailTest() throws Exception{
        String email = "test@email.com";
        String password = "123456789";
        Member member = this.createTestMember(email,password);

        System.out.println("로그인 성공 테스트 :" + member.toString());

        mockMvc.perform(
                        formLogin()                                         // form 태그 기반의 로그인 인증 방식 테스트
                                .loginProcessingUrl("/members/login")       // 요청 url
                                .userParameter("email")      // 로그인시 파라미터 email로 설정
                                .user(email).password("1234567890"))         // 사용 email, password
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }

}
