package com.example.giftshop.member.service;

import com.example.giftshop.cart.entity.Cart;
import com.example.giftshop.cart.repository.CartGoodsRepository;
import com.example.giftshop.cart.repository.CartRepository;
import com.example.giftshop.member.dto.MemberActiveDTO;
import com.example.giftshop.member.entity.Member;
import com.example.giftshop.member.repository.MemberRepository;
import com.example.giftshop.orders.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartGoodsRepository cartGoodsRepository;
    private final OrdersRepository ordersRepository;

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
    @Override
    public MemberActiveDTO getMemberActive(String email){

        Member member = memberRepository.findByEmail(email); //회원 정보

        Cart cart = cartRepository.findByMemberId(member.getId()); //회원 장바구니

        Long cartCount = cartGoodsRepository.countByCartId(cart.getId()); //장바구니 개수
        Long totalCount = ordersRepository.countOrder(email); //주문 총 개수
        Long canceled = ordersRepository.countOrderCancel(email); //주문 취소

        return MemberActiveDTO.builder()
                .cart(cartCount)
                .total(totalCount)
                .canceled(canceled).build();

    }

}
