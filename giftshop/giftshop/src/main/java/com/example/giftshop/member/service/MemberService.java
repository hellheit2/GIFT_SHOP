package com.example.giftshop.member.service;

import com.example.giftshop.member.dto.MemberFormDTO;
import com.example.giftshop.member.entity.Member;

public interface MemberService {

    public Member joinMember(Member member);

    public void validateDuplicateMember(Member member);

    public int deleteMember(Member member);
}
