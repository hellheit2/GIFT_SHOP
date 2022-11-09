package com.example.giftshop.goods.repository;

import com.example.giftshop.goods.entity.GoodsImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodsImageRepository extends JpaRepository<GoodsImage, Long> {

    List<GoodsImage> findByGoodsIdOrderByIdAsc(Long goodsId);
}
