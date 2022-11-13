package com.example.giftshop.cart.service;

import com.example.giftshop.cart.dto.CartDetailDTO;
import com.example.giftshop.cart.dto.CartGoodsDTO;
import com.example.giftshop.cart.dto.CartOrderDTO;
import com.example.giftshop.cart.entity.Cart;
import com.example.giftshop.cart.entity.CartGoods;
import com.example.giftshop.goods.entity.Goods;
import com.example.giftshop.member.entity.Member;
import com.example.giftshop.orders.dto.OrderDTO;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

public interface CartService {

    public Long addCart(CartGoodsDTO cartGoodsDTO, String email);
    public List<CartDetailDTO> getCartList(String email);
    public boolean validateCartGoods(Long cartGoodsId, String email);
    public void updateCartGoodsCount(Long cartGoodsId, int count);
    public void deleteCartGoods(Long cartGoodsId);
    public Long orderCartGoods(List<CartOrderDTO> cartOrderDTOList, String email);

}
