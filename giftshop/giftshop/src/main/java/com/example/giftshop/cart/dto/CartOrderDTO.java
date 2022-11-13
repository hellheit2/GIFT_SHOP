package com.example.giftshop.cart.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartOrderDTO {

    private Long cartGoodsId; //장바구니 상품 Id
    private List<CartOrderDTO> cartOrderDTOList; //
}
