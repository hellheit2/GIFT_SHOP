package com.example.giftshop.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberFormDTO {

    private String name; //이름

    private String email; //이메일

    private String password; //비밀번호

    private String address; //주소

    private String phoneNumber; //전화번호

    private String sex; //성별

    private int age; //나이

    @Builder
    public MemberFormDTO(String name, String email, String password, String address, String phoneNumber, String sex, int age) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
        this.age = age;
    }
}
