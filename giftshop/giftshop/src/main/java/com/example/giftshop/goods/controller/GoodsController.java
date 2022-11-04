package com.example.giftshop.goods.controller;

import com.example.giftshop.goods.dto.GoodsDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping(value="/goods")
public class GoodsController {


    @GetMapping(value="/test")
    public String thymeleafTest(Model model){
        GoodsDTO goodsDTO = GoodsDTO.builder()
                .goodsDetail("상품 상세 설명")
                .goodsName("테스트1")
                .goodsPrice(1000)
                .regTime(LocalDateTime.now())
                .build();

        model.addAttribute("goodsDTO", goodsDTO);
        return "board/test";

    }
}
