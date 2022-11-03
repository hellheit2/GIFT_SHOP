package com.example.giftshop.goods.repository;

import com.example.giftshop.goods.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<Goods, Long> {


}
