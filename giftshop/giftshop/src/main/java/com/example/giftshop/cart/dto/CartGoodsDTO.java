package com.example.giftshop.cart.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CartGoodsDTO {

    @NotNull(message = "상품 아이디는 필수 입력 값 입니다.")
    private Long goodsId;

    @Min(value = 1, message = "상품을 담아주세요")
    private int count;
}
