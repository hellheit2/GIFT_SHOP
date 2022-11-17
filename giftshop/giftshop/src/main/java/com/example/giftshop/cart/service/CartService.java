package com.example.giftshop.cart.service;

import com.example.giftshop.cart.dto.CartDetailDTO;
import com.example.giftshop.cart.dto.CartGoodsDTO;
import com.example.giftshop.cart.dto.CartOrderDTO;

import java.util.List;

public interface CartService {

    Long addCart(CartGoodsDTO cartGoodsDTO, String email);
    List<CartDetailDTO> getCartList(String email);
    boolean validateCartGoods(Long cartGoodsId, String email);
    void updateCartGoodsCount(Long cartGoodsId, int count);
    void deleteCartGoods(Long cartGoodsId);
    Long orderCartGoods(List<CartOrderDTO> cartOrderDTOList, String email);

}
