package com.example.giftshop.cart.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartOrderDTO {
    //멤버변수 초기화 과정 없을시 null
    private Long cartGoodsId; //장바구니 상품 Id
    private List<CartOrderDTO> cartOrderDTOList; //cartGoodsId map형식으로 리스트 이용 가능
}
