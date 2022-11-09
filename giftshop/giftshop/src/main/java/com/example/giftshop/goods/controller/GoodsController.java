package com.example.giftshop.goods.controller;

import com.example.giftshop.goods.dto.GoodsFormDTO;
import com.example.giftshop.goods.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
/*@RequestMapping(value="/goods")*/
public class GoodsController {


    /*
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
    */

    private final GoodsService goodsService;

    @GetMapping(value = "/admin/goods/register")
    public String goodsRegisterForm(Model model){
        model.addAttribute("goodsFormDTO", GoodsFormDTO.builder().build());
        return "goods/registerform";
    }

    @PostMapping(value = "/admin/goods/register")
    public String goodsRegister(@Valid GoodsFormDTO goodsFormDTO,
                                BindingResult bindingResult,
                                Model model,
                                @RequestParam("goodsImage") List<MultipartFile> goodsImageList){

        //상품 등록시 필수 값이 없을 경우
        if(bindingResult.hasErrors()){
            return "goods/registerform";
        }
        //이미지가 없을 경우(필수 값), 넘어온 DTO가 없을 경우
        if(goodsImageList.get(0).isEmpty() && goodsFormDTO.getId() == null){
            model.addAttribute("errorMessage", "상품 이미지를 1개 이상 등록해 주세요.");
        }

        try{
            goodsService.uploadGoods(goodsFormDTO,goodsImageList);
        }catch(Exception e){
            model.addAttribute("errorMessage", "상품 등록에 실패했습니다.");
            return "goods/registerform";
        }
        return "redirect:/";
    }

    @GetMapping(value = "/admin/goods/{goodsId}")
    public String getGoodsDetail(@PathVariable("goodsId") Long goodsId, Model model){

        try {
            GoodsFormDTO goodsFormDTO = goodsService.getGoodsDetail(goodsId);
            model.addAttribute("goodsFormDTO", goodsFormDTO);
        }catch (EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
            model.addAttribute("goodsFormDTO", GoodsFormDTO.builder().build());
            return "goods/registerform";
        }
        return "goods/registerform";
    }

    @PostMapping(value = "/admin/goods/{goodsId}")
    public String goodsUpdate(@Valid GoodsFormDTO goodsFormDTO,
                              BindingResult bindingResult,
                              @RequestParam("goodsImage") List<MultipartFile> goodsImageList,
                              Model model){

        //상품 등록시 필수 값이 없을 경우
        if(bindingResult.hasErrors()) {
            return "goods/registerform";
        }
        //이미지가 없을 경우(필수 값), 넘어온 DTO가 없을 경우
        if(goodsImageList.get(0).isEmpty() && goodsFormDTO.getId() == null){
            model.addAttribute("errorMessage", "상품 이미지를 1개 이상 등록해 주세요.");
            return "goods/registerform";
        }

        try{
            System.out.println(goodsFormDTO.toString());
            goodsService.updateGoods(goodsFormDTO, goodsImageList);
        }catch(Exception e){
            model.addAttribute("errorMessage", "상품 수정에 실패했습니다.");
            return "goods/registerform";
        }

        return "redirect:/";
    }

}
