package com.example.giftshop.goods.service;

import com.example.giftshop.goods.dto.GoodsFormDTO;
import com.example.giftshop.goods.dto.GoodsSearchDTO;
import com.example.giftshop.goods.entity.Goods;
import com.example.giftshop.goods.dto.GoodsWrapDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GoodsService {

    Long uploadGoods(GoodsFormDTO goodsFormDTO,List<MultipartFile> goodsImageList) throws Exception; //상품 등록
    GoodsFormDTO getGoodsDetail(Long goodsId); //상품 상세 조회
    Long updateGoods(GoodsFormDTO goodsFormDTO, List<MultipartFile> goodsImageList) throws Exception; //상품 수정
    Page<Goods> getAdminGoodsPage(GoodsSearchDTO goodsSearchDTO, Pageable pageable); //관리자 상품 페이징
    Page<GoodsWrapDTO> getAllGoodsList(GoodsSearchDTO goodsSearchDTO, Pageable pageable); //상품 전체 목록
    Page<GoodsWrapDTO> getMainGoodsPage(GoodsSearchDTO goodsSearchDTO, Pageable pageable); //메인페이지 상품 리스트
}
