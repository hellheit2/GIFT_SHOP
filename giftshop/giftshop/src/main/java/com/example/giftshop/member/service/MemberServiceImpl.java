package com.example.giftshop.member.service;

import com.example.giftshop.member.dto.MemberFormDTO;
import com.example.giftshop.member.entity.Member;
import com.example.giftshop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public Member joinMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    @Override
    public void validateDuplicateMember(Member member) {

        Optional<Member> checkMember = Optional.ofNullable(
                memberRepository.findByEmail(member.getEmail())
        );

        checkMember.ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    @Override
    public int deleteMember(Member member) {
        return 0;
    }


    /* UserDetailService */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(email);

        if(member == null){
            throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }
}
