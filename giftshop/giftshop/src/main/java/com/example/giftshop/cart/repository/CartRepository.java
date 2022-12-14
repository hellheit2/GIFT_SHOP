package com.example.giftshop.cart.repository;

import com.example.giftshop.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByMemberId(Long memberId); //회원의 장바구니


}
