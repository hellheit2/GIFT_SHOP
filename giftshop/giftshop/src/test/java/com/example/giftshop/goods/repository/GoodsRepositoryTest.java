package com.example.giftshop.goods.repository;

import com.example.giftshop.goods.constant.GoodsSellStatus;
import com.example.giftshop.goods.entity.Goods;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class GoodsRepositoryTest {

    @Autowired
    GoodsRepository goodsRepository;

    @Test
    @DisplayName("상품 추가 테스트")
    public void createGoodsTest(){
        Goods goods = new Goods();
        goods.setGoods_name("테스트 상품");
        goods.setGoods_price(10000);
        goods.setGoods_detail("테스트 상품 상세 설명");
        goods.setSellStatus(GoodsSellStatus.SELL);
        goods.setGoods_regTime(LocalDateTime.now());
        Goods savedGoods = goodsRepository.save(goods);
        System.out.println(savedGoods.toString());
    }

}