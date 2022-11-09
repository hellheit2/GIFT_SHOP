package com.example.giftshop.goods.repository;

import com.example.giftshop.goods.constant.GoodsSellStatus;
import com.example.giftshop.goods.dto.GoodsSearchDTO;
import com.example.giftshop.goods.entity.Goods;
import com.example.giftshop.goods.entity.QGoods;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

public class GoodsRepositoryCustomImpl implements GoodsRepositoryCustom{

    private JPAQueryFactory queryFactory;

    public GoodsRepositoryCustomImpl(EntityManager em){
        this.queryFactory =new JPAQueryFactory(em);
    }

    private BooleanExpression searchSellStatusEq(GoodsSellStatus searchSellstatus){
        return searchSellstatus == null ? null : QGoods.goods.goodsSellStatus.eq(searchSellstatus);
    }

    @Override
    public Page<Goods> getAdminGoodsPage(GoodsSearchDTO goodsSearchDTO, Pageable pageable) {

        return null;
    }
}
