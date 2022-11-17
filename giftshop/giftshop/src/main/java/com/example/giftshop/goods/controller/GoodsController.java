package com.example.giftshop.goods.controller;

import com.example.giftshop.goods.dto.GoodsFormDTO;
import com.example.giftshop.goods.dto.GoodsSearchDTO;
import com.example.giftshop.goods.entity.Goods;
import com.example.giftshop.goods.service.GoodsService;
import com.example.giftshop.goods.dto.GoodsWrapDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class GoodsController {


    private final GoodsService goodsService;

    @GetMapping(value = "/admin/goods/register")
    public String goodsRegisterForm(Model model){
        //상품 등록
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
    public String goodsModify(@PathVariable("goodsId") Long goodsId, Model model){
        //관리자 상품 관리
        try{
            GoodsFormDTO goodsFormDTO = goodsService.getGoodsDetail(goodsId);
            model.addAttribute("goodsFormDTO", goodsFormDTO);
        }catch(EntityNotFoundException e){
            model.addAttribute("errorMessage","존재하지 않는 상품입니다.");
            model.addAttribute("goodsFormDTO", new GoodsFormDTO());
            return "goods/registerform";
        }

        return "goods/registerform";
    }

    @GetMapping(value = "/goods/{goodsId}")
    public String getGoodsDetail(@PathVariable("goodsId") Long goodsId, Model model){
        //상품 상세 설명
        GoodsFormDTO goodsFormDTO = goodsService.getGoodsDetail(goodsId);
        model.addAttribute("goods", goodsFormDTO);
        return "goods/detail";
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

    @GetMapping(value = {"/admin/goods-list", "/admin/goods-list/{page}"})
    public String goodsManage(GoodsSearchDTO goodsSearchDTO,
                              @PathVariable("page") Optional<Integer> page,
                              Model model){
        //페이지가 존재할 경우 해당 페이지(page.get()), 아닐 경우 0
        Pageable pageable = PageRequest.of(page.isPresent()? page.get() : 0, 4);

        Page<Goods> pageInfo = goodsService.getAdminGoodsPage(goodsSearchDTO, pageable); //Page 객체
        System.out.println(pageInfo.getTotalPages());
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("goodsSearchDTO", goodsSearchDTO);
        model.addAttribute("maxPage",5);

        return "goods/goodsmanage";
    }

    @GetMapping(value = {"/goods/goods-list", "/goods/goods-list/{page}"})
    public String goodsList(GoodsSearchDTO goodsSearchDTO,
                              @PathVariable("page") Optional<Integer> page,
                              Model model){
        //페이지가 존재할 경우 해당 페이지(page.get()), 아닐 경우 0
        Pageable pageable = PageRequest.of(page.isPresent()? page.get() : 0, 12);

        Page<GoodsWrapDTO> pageInfo = goodsService.getAllGoodsList(goodsSearchDTO, pageable); //Page 객체

        System.out.println(pageInfo.getTotalPages());
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("goodsSearchDTO", goodsSearchDTO);
        model.addAttribute("maxPage",5);

        return "goods/goodslist";
    }


}
