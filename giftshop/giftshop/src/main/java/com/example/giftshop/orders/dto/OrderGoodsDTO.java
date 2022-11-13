package com.example.giftshop.orders.dto;

import com.example.giftshop.orders.entity.OrderGoods;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderGoodsDTO {

    private String goodsName; //상품명
    private int count; //주문 수량
    private int orderPrice; //주문 금액
    private String imgUrl; //상품 이미지 경로

    public OrderGoodsDTO(OrderGoods orderGoods, String imgUrl){
        this.goodsName = orderGoods.getGoods().getGoodsName();
        this.count = orderGoods.getCount();
        this.orderPrice = orderGoods.getOrderPrice();
        this.imgUrl = imgUrl;
    }


}
