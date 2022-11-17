package com.example.giftshop.member.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberFormDTO {

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name; //이름

    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email; //이메일

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min = 8,max = 20, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요.")
    private String password; //비밀번호

    private String address; //주소

    private String phoneNumber; //전화번호

    private String sex; //성별

    private int age; //나이
}
