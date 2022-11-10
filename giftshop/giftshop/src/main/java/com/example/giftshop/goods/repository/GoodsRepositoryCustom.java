package com.example.giftshop.goods.repository;

import com.example.giftshop.goods.dto.GoodsSearchDTO;
import com.example.giftshop.goods.entity.Goods;
import com.example.giftshop.main.dto.MainGoodsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GoodsRepositoryCustom {

    Page<Goods> getAdminGoodsPage(GoodsSearchDTO goodsSearchDTO, Pageable pageable);
    Page<MainGoodsDTO> getMainGoodsPage(GoodsSearchDTO goodsSearchDTO, Pageable pageable);
}
