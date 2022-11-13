package com.example.giftshop.orders.controller;

import com.example.giftshop.orders.dto.OrderDTO;
import com.example.giftshop.orders.dto.OrderHistoryDTO;
import com.example.giftshop.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(value = "/order")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid OrderDTO orderDTO,
                                              BindingResult bindingResult, Principal principal){

        if(bindingResult.hasErrors()){ //전달 받은 OrderDTO 객체 바인딩 에러
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for(FieldError fieldError : fieldErrors){
                sb.append(fieldError.getDefaultMessage()); //에러 정보 저장
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST); //에러 전달
        }
        String email = principal.getName(); //Principal 객체로 로그인 유저 정보 접근
        Long orderId;

        try{
            orderId = orderService.order(orderDTO, email); //주문 호출
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); //실패시 에러
        }
        return new ResponseEntity<>(orderId, HttpStatus.OK); //성공 응답 반환
    }

    @GetMapping(value = {"/orders", "/orders/{page}"})
    public String orderHistory(@PathVariable("page")Optional<Integer> page,
                               Principal principal, Model model) {

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 4); //페이징 정보
        Page<OrderHistoryDTO> ordersHistoryDTOList
                = orderService.getOrderList(principal.getName(), pageable); //주문 목록(이메일, 페이징 정보)

        model.addAttribute("orders", ordersHistoryDTOList); //주문목록 리스트
        model.addAttribute("page", pageable.getPageNumber()); //페이징 정보
        model.addAttribute("maxPage", 5); //한번에 보일 페이지 크기

        return "order/history";
    }

    @PostMapping("/order/{orderId}/cancel")
    public @ResponseBody ResponseEntity cancelOrder(@PathVariable("orderId") Long orderId , Principal principal){

        if(!orderService.validateOrder(orderId, principal.getName())){
            return new ResponseEntity<String>("주문 취소 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        orderService.cancelOrder(orderId);
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }
}
