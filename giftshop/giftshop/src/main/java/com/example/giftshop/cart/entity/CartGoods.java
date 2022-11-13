package com.example.giftshop.cart.entity;

import com.example.giftshop.common.entity.BaseEntity;
import com.example.giftshop.goods.entity.Goods;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class CartGoods extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cart_goods_id")
    private Long id; //장바구니 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart; //장바구니

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods; //상품

    private int count; //개수

    public static CartGoods createCartGoods(Cart cart, Goods goods, int count){
        CartGoods cartGoods = new CartGoods(); //장바구니, 상품, 개수 세팅
        cartGoods.setCart(cart);
        cartGoods.setGoods(goods);
        cartGoods.setCount(count);
        return cartGoods;
    }

    public void addCount(int count){ //이미 있을 경우
        this.count += count; //수량 추가
    }

    public void updateCount(int count){
        this.count = count;//수량 변경 적용
    }
}

