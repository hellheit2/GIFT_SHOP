package com.example.giftshop.main.controller;

import com.example.giftshop.goods.dto.GoodsSearchDTO;
import com.example.giftshop.goods.service.GoodsService;
import com.example.giftshop.main.dto.MainGoodsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final GoodsService goodsService;
    @GetMapping(value="/")
    public String main(GoodsSearchDTO goodsSearchDTO, Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of((page.isPresent() ? page.get() : 0), 4);
        Page<MainGoodsDTO> pageInfo = goodsService.getMainGoodsPage(goodsSearchDTO,pageable);

        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("goodsSearchDTO", goodsSearchDTO);
        model.addAttribute("maxPage", 5);

        return "main";
    }

}
