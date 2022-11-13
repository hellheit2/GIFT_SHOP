package com.example.giftshop.cart.controller;

import com.example.giftshop.cart.dto.CartDetailDTO;
import com.example.giftshop.cart.dto.CartGoodsDTO;
import com.example.giftshop.cart.dto.CartOrderDTO;
import com.example.giftshop.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping(value = "/cart")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid CartGoodsDTO cartGoodsDTO,
                                              BindingResult bindingResult, Principal principal){
        //장바구니 추가
        if(bindingResult.hasErrors()){ //CartGoodsDTO 바인딩 에러
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors(); //

            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage()); //에러 스트링 결합
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST); //에러, 응답 결과 리턴
        }

        String email = principal.getName(); //로그인 회원 확인
        Long cartGoodsId;

        try {
            cartGoodsId = cartService.addCart(cartGoodsDTO, email); //상품 장바구니 추가
        } catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST); //실패
        }
        return new ResponseEntity<Long>(cartGoodsId, HttpStatus.OK); //성공
    }

    @GetMapping(value = "/cart")
    public String orderHistory(Principal principal, Model model){
        //장바구니 페이지
        List<CartDetailDTO> cartDetailList = cartService.getCartList(principal.getName()); //로그인 회원 장바구니 리스트
        model.addAttribute("cartGoodsList", cartDetailList);
        return "cart/cartlist";
    }

    @PatchMapping(value = "/cartgoods/{cartGoodsId}")
    public @ResponseBody ResponseEntity updateCartItem(@PathVariable("cartGoodsId") Long cartGoodsId,
                                                       int count, Principal principal){
        //장바구니 수량 최신화
        if(count <= 0){ //수량이 0이하일 경우
            return new ResponseEntity<String>("최소 1개 이상 담아주세요", HttpStatus.BAD_REQUEST);
        } else if(!cartService.validateCartGoods(cartGoodsId, principal.getName())){ //로그인 회원과 장바구니 회원 다를 경우
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN); //권한 없음
        }
        cartService.updateCartGoodsCount(cartGoodsId, count); //수량 최신화
        return new ResponseEntity<Long>(cartGoodsId, HttpStatus.OK);
    }


    @DeleteMapping(value = "/cartGoods/{cartGoodsId}")
    public @ResponseBody ResponseEntity deleteCartItem(@PathVariable("cartGoodsId") Long cartGoodsId, Principal principal){
        //장바구니 삭제
        if(!cartService.validateCartGoods(cartGoodsId, principal.getName())){ //로그인 회원과 장바구니 회원 다를 경우
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN); //권한 없음
        }
        cartService.deleteCartGoods(cartGoodsId); //장바구니 제거

        return new ResponseEntity<Long>(cartGoodsId, HttpStatus.OK);
    }

    @PostMapping(value = "/cart/orders")
    public @ResponseBody ResponseEntity orderCartGoods(@RequestBody CartOrderDTO cartOrderDTO, Principal principal){
        //장바구니 상품 주문
        List<CartOrderDTO> cartOrderDTOList = cartOrderDTO.getCartOrderDTOList(); //장바구니 주문 목록

        if(cartOrderDTOList == null || cartOrderDTOList.size() == 0){ //주문 목록이 null 이거나 장바구니 체크가 없는 경우
            return new ResponseEntity<String>("주문할 상품을 선택해주세요", HttpStatus.FORBIDDEN);
        }

        for (CartOrderDTO cartOrder : cartOrderDTOList) { //각 주문 유효성 확인
            if(!cartService.validateCartGoods(cartOrder.getCartGoodsId(), principal.getName())){ //장바구니 유효성 확인
                return new ResponseEntity<String>("주문 권한이 없습니다.", HttpStatus.FORBIDDEN);
            }
        }

        Long orderId = cartService.orderCartGoods(cartOrderDTOList, principal.getName()); //주문 결과 리턴

        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }
}
