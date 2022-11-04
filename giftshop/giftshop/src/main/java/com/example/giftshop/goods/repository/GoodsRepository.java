package com.example.giftshop.goods.repository;

import com.example.giftshop.goods.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface GoodsRepository extends JpaRepository<Goods, Long>,
        QuerydslPredicateExecutor<Goods> {

    List<Goods> findByGoodsName(String goodsName);
    List<Goods> findByGoodsPriceBetween(Integer minPrice, Integer maxPrice);
}
