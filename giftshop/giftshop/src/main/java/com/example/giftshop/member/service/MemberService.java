package com.example.giftshop.member.service;

import com.example.giftshop.member.dto.MemberActiveDTO;
import com.example.giftshop.member.dto.MemberFormDTO;
import com.example.giftshop.member.entity.Member;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface MemberService extends UserDetailsService {

    public Member joinMember(Member member);

    public void validateDuplicateMember(Member member);

    public int deleteMember(Member member);

    /* UserDetailService */
    public UserDetails loadUserByUsername(String email);

    public MemberActiveDTO getMemberActive(String email);
}
