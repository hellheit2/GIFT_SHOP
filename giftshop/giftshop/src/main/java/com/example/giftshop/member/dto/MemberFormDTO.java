package com.example.giftshop.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberFormDTO {

    private String name; //이름

    private String email; //이메일

    private String password; //비밀번호

    private String address; //주소

    private String phoneNumber; //전화번호

    private String sex; //성별

    private int age; //나이
}
