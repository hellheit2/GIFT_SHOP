package com.example.giftshop.member.service;

import com.example.giftshop.member.dto.MemberFormDTO;
import com.example.giftshop.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createTestMember() {
        MemberFormDTO memberFormDTO = MemberFormDTO.builder()
                .email("test@gmail.com")
                .name("테스터1")
                .address("테스트 주소")
                .password("12345").build();
        return Member.createMember(memberFormDTO, passwordEncoder);
    }

    @Test
    @DisplayName("회원가입 테스트")
    void joinMemberTest() {
        Member member = createTestMember();
        Member joinedMember = memberService.joinMember(member);

        assertEquals(member.getEmail(), joinedMember.getEmail());
        assertEquals(member.getName(), joinedMember.getName());
        assertEquals(member.getAddress(), joinedMember.getAddress());
        assertEquals(member.getPassword(), joinedMember.getPassword());
        assertEquals(member.getRole(), joinedMember.getRole());
    }

    @Test
    @DisplayName("중복 가입 확인 테스트")
    void validateDuplicateMember() {
        Member member1 = createTestMember();
        Member member2 = createTestMember();
        memberService.joinMember(member1);

        assertThrows(
                IllegalStateException.class, () -> memberService.joinMember(member2)
        );

    }

    @Test
    void deleteMember() {
    }
}