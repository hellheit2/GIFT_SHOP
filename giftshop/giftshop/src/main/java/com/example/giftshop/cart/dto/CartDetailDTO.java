package com.example.giftshop.cart.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDetailDTO {
    private Long cartGoodsId; //cartGoods 아이디
    private String goodsName; //상품명
    private int price; //가격
    private int count; //개수
    private String imgUrl; //이미지 경로

    public CartDetailDTO(Long cartGoodsId, String goodsName, int price, int count, String imgUrl){
        //장바구니 상세 페이지로 전달할 데이터
        this.cartGoodsId = cartGoodsId;
        this.goodsName = goodsName;
        this.price = price;
        this.count = count;
        this.imgUrl = imgUrl;
    }
}
