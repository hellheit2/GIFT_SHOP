package com.example.giftshop.cart.service;

import com.example.giftshop.cart.dto.CartGoodsDTO;
import com.example.giftshop.cart.entity.CartGoods;
import com.example.giftshop.cart.repository.CartGoodsRepository;
import com.example.giftshop.goods.constant.GoodsSellStatus;
import com.example.giftshop.goods.entity.Goods;
import com.example.giftshop.goods.repository.GoodsRepository;
import com.example.giftshop.member.entity.Member;
import com.example.giftshop.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class CartServiceTest {

    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CartService cartService;
    @Autowired
    CartGoodsRepository cartGoodsRepository;

    public Goods saveGoods(){ //테스트 상품 생성
        Goods goods = Goods.builder()
                .goodsName("테스트 상품")
                .goodsPrice(10000)
                .goodsStock(100)
                .goodsDetail("테스트 상품 설명")
                .goodsSellStatus(GoodsSellStatus.SELL).build();

        return goodsRepository.save(goods);
    }

    public Member saveMember(){ //테스트 멤버 생성
        Member member = new Member();
        member.setEmail("test@test.com");
        return memberRepository.save(member);
    }

    @Test
    @DisplayName("장바구니 담기 테스트")
    public void addCart(){
        Goods goods = saveGoods(); //테스트 상품
        Member member = saveMember(); //테스트 멤버

        CartGoodsDTO cartGoodsDTO = new CartGoodsDTO(); //장바구니 상품
        cartGoodsDTO.setCount(5); //개수 등록
        cartGoodsDTO.setGoodsId(goods.getId()); //상품 등록

        Long cartItemId = cartService.addCart(cartGoodsDTO, member.getEmail()); //장바구니 추가
        CartGoods cartGoods = cartGoodsRepository.findById(cartItemId) //추가한 상품 검색
                .orElseThrow(EntityNotFoundException::new);

        assertEquals(goods.getId(), cartGoods.getGoods().getId()); //상품 일치 확인
        assertEquals(cartGoodsDTO.getCount(), cartGoods.getCount()); //개수 일치 확인
    }

}
