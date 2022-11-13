package com.example.giftshop.cart.entity;

import com.example.giftshop.common.entity.BaseEntity;
import com.example.giftshop.member.entity.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class Cart extends BaseEntity {

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static Cart createCart(Member member){ //주문자 장바구니
        Cart cart = new Cart();
        cart.setMember(member);
        return cart;
    }
}
