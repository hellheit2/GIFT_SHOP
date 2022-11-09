package com.example.giftshop.goods.service;

import com.example.giftshop.goods.dto.GoodsFormDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GoodsService {

    public Long uploadGoods(GoodsFormDTO goodsFormDTO,List<MultipartFile> goodsImageList) throws Exception;

    public GoodsFormDTO getGoodsDetail(Long goodsId);

    public Long updateGoods(GoodsFormDTO goodsFormDTO, List<MultipartFile> goodsImageList) throws Exception;
}
