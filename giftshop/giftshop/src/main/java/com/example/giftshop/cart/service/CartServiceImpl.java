package com.example.giftshop.cart.service;

import com.example.giftshop.cart.dto.CartDetailDTO;
import com.example.giftshop.cart.dto.CartGoodsDTO;
import com.example.giftshop.cart.dto.CartOrderDTO;
import com.example.giftshop.cart.entity.Cart;
import com.example.giftshop.cart.entity.CartGoods;
import com.example.giftshop.cart.repository.CartGoodsRepository;
import com.example.giftshop.cart.repository.CartRepository;
import com.example.giftshop.common.exception.OutOfStockException;
import com.example.giftshop.goods.entity.Goods;
import com.example.giftshop.goods.repository.GoodsRepository;
import com.example.giftshop.member.entity.Member;
import com.example.giftshop.member.repository.MemberRepository;
import com.example.giftshop.orders.dto.OrderDTO;
import com.example.giftshop.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final GoodsRepository goodsRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartGoodsRepository cartGoodsRepository;
    private final OrderService orderService;

    public Long addCart(CartGoodsDTO cartGoodsDTO, String email){
        //장바구니 추가
        Goods goods = goodsRepository.findById(cartGoodsDTO.getGoodsId()) //담을 상품
                .orElseThrow(EntityNotFoundException::new); //못찾을 경우
        Member member = memberRepository.findByEmail(email); //회원

        Cart cart = cartRepository.findByMemberId(member.getId()); //회원 장바구니
        if(cart == null){ //장바구니 없을 경우(첫 장바구니)
            cart = Cart.createCart(member); //장바구니 생성
            cartRepository.save(cart); //저장
        }

        if(goods.getGoodsStock() < cartGoodsDTO.getCount()){
            throw new OutOfStockException("상품의 재고가 부족합니다." +
                    "(현재 재고 수량 : " + goods.getGoodsStock() + ")");
        }

        CartGoods savedCartGoods = cartGoodsRepository.findByCartIdAndGoodsId(cart.getId(), goods.getId()); //장바구니 확인

        if(savedCartGoods != null){ //장바구니에 있을 경우
            savedCartGoods.addCount(cartGoodsDTO.getCount()); //장바구니에 추가(수량 추가)
            return savedCartGoods.getId();
        } else { //장바구니에 없을 경우
            CartGoods cartGoods = CartGoods.createCartGoods(cart, goods, cartGoodsDTO.getCount()); //장바구니,상품,개수로 엔티티 생성
            cartGoodsRepository.save(cartGoods); //장바구니 상품 저장
            return cartGoods.getId();
        }
    }

    @Transactional(readOnly = true)
    public List<CartDetailDTO> getCartList(String email){
        //장바구니 조회
        List<CartDetailDTO> cartDetailDTOList = new ArrayList<>(); //장바구니 내용물 저장 리스트

        Member member = memberRepository.findByEmail(email); //회원 정보
        Cart cart = cartRepository.findByMemberId(member.getId()); //회원 정보로 장바구니 조회
        if(cart == null){ //장바구니 x
            return cartDetailDTOList; //조회된 정보 x
        }
        cartDetailDTOList = cartGoodsRepository.findCartDetailDTOList(cart.getId()); //장바구니 내용물 검색
        return cartDetailDTOList; //조회 정보 리턴
    }

    @Transactional(readOnly = true)
    public boolean validateCartGoods(Long cartGoodsId, String email){
        //장바구니 유효성 확인
        Member curMember = memberRepository.findByEmail(email); //로그인 회원 조회
        CartGoods cartGoods = cartGoodsRepository.findById(cartGoodsId) //장바구니 상품 조회
                .orElseThrow(EntityNotFoundException::new);
        Member savedMember = cartGoods.getCart().getMember(); //상품이 담긴 장바구니의 회원 조회

        if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())){ //일치 확인
            return false;
        }
        return true;
    }

    public void updateCartGoodsCount(Long cartGoodsId, int count){
        //장바구니 수량 최신화
        CartGoods cartGoods = cartGoodsRepository.findById(cartGoodsId) //장바구니 상품 조회
                .orElseThrow(EntityNotFoundException::new);

        cartGoods.updateCount(count); //수량 최신화
    }

    public void deleteCartGoods(Long cartGoodsId) {
        //장바구니 목록 제거
        CartGoods cartGoods = cartGoodsRepository.findById(cartGoodsId) //장바구니 상품 조회
                .orElseThrow(EntityNotFoundException::new);
        cartGoodsRepository.delete(cartGoods); //장바구니에서 제거(DB 삭제)
    }

    public Long orderCartGoods(List<CartOrderDTO> cartOrderDTOList, String email){
        //장바구니로 상품 주문
        List<OrderDTO> orderDTOList = new ArrayList<>(); //주문 리스트

        for (CartOrderDTO cartOrderDTO : cartOrderDTOList) { //장바구니 주문 리스트
            CartGoods cartGoods = cartGoodsRepository.findById(cartOrderDTO.getCartGoodsId()) //장바구니 상품 확인
                    .orElseThrow(EntityNotFoundException::new);

            OrderDTO orderDTO = new OrderDTO(); //주문 세팅
            orderDTO.setGoodsId(cartGoods.getGoods().getId()); //상품 아이디
            orderDTO.setCount(cartGoods.getCount()); //개수
            orderDTOList.add(orderDTO); //주문 저장

            cartGoodsRepository.delete(cartGoods); //주문 완료 상품 장바구니 제거
        }

        Long orderId = orderService.orderByCart(orderDTOList, email); //주문 후 주문 id 리턴

        return orderId;
    }
}
