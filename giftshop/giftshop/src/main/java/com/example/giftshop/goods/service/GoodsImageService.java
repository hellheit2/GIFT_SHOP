package com.example.giftshop.goods.service;

import com.example.giftshop.goods.entity.GoodsImage;
import org.springframework.web.multipart.MultipartFile;

public interface GoodsImageService {

    public void saveGoodsImage(GoodsImage goodsImage, MultipartFile goodsImgFile) throws Exception;

    public void updateGoodsImg(Long goodsImgId, MultipartFile goodsImage) throws Exception;
}
