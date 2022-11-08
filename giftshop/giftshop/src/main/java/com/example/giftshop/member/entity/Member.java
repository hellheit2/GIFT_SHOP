package com.example.giftshop.member.entity;

import com.example.giftshop.common.entity.BaseEntity;
import com.example.giftshop.member.constant.Role;
import com.example.giftshop.member.dto.MemberFormDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name="t_member")
@Getter
@Setter
@ToString
public class Member extends BaseEntity {

    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name; //이름

    @Column(unique = true)
    private String email; //이메일

    private String password; //비밀번호

    private String address; //주소

    private String phoneNumber; //전화번호

    private String sex; //성별

    private int age; //나이

    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member createMember(MemberFormDTO memberFormDTO, PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setName(memberFormDTO.getName());
        member.setEmail(memberFormDTO.getEmail());
        member.setAddress(memberFormDTO.getAddress());
        String password = passwordEncoder.encode(memberFormDTO.getPassword());
        member.setPassword(password);
        member.setRole(Role.USER);
        return member;
    }
}
