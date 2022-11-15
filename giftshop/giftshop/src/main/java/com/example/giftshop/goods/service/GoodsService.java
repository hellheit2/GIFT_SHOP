package com.example.giftshop.goods.service;

import com.example.giftshop.goods.dto.GoodsFormDTO;
import com.example.giftshop.goods.dto.GoodsSearchDTO;
import com.example.giftshop.goods.entity.Goods;
import com.example.giftshop.main.dto.MainGoodsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GoodsService {

    public Long uploadGoods(GoodsFormDTO goodsFormDTO,List<MultipartFile> goodsImageList) throws Exception; //상품 등록
    public GoodsFormDTO getGoodsDetail(Long goodsId); //상품 상세 조회
    public Long updateGoods(GoodsFormDTO goodsFormDTO, List<MultipartFile> goodsImageList) throws Exception; //상품 수정
    public Page<Goods> getAdminGoodsPage(GoodsSearchDTO goodsSearchDTO, Pageable pageable); //관리자 상품 페이징
    public Page<Goods> getAllGoodsList(GoodsSearchDTO goodsSearchDTO, Pageable pageable); //상품 전체 목록
    public Page<MainGoodsDTO> getMainGoodsPage(GoodsSearchDTO goodsSearchDTO, Pageable pageable); //메인페이지 상품 리스트
}
