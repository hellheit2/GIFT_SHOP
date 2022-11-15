package com.example.giftshop.goods.repository;

import com.example.giftshop.category.repository.CategoryRepository;
import com.example.giftshop.goods.constant.GoodsSellStatus;
import com.example.giftshop.category.entity.Category;
import com.example.giftshop.goods.entity.Goods;
import com.example.giftshop.goods.entity.QGoods;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Slf4j
class GoodsRepositoryTest {

    @Autowired
    GoodsRepository goodsRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("상품 추가 테스트")
    public void createGoodsTest(){
        Goods goods = Goods.builder()
                .goodsName("테스트 상품")
                .goodsPrice(10000)
                .goodsStock(100)
                .goodsDetail("테스트 상품 상세 설명")
                .goodsSellStatus(GoodsSellStatus.SELL).build();

        Goods savedGoods = goodsRepository.save(goods);
        log.info(savedGoods.toString());

    }

    //@BeforeEach
    public void createGoodsList(){
        for(int i=1;i<=200;i++){
            Goods goods = Goods.builder()
                    .goodsName("테스트 상품" + i)
                    .goodsPrice(10000 + i)
                    .goodsDetail("테스트 상품 상세 설명" + i)
                    .goodsSellStatus(GoodsSellStatus.SELL).build();

            Goods savedGoods = goodsRepository.save(goods);
        }
    }
    
    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByGoodsTest(){
        List<Goods> goodsList = goodsRepository.findByGoodsName("테스트 상품1");
        for(Goods goods : goodsList){
            log.info(goods.toString());
        }
    }

    @Test
    @DisplayName("가격 LessThan 테스트")
    public void findByPriceRangeTest(){
        List<Goods> goodsList = goodsRepository.findByGoodsPriceBetween(10002,10006);
        for(Goods goods : goodsList){
            log.info(goods.toString());
        }
    }

    @Test
    @DisplayName("Querydsl 조회 테스트")
    public void queryDslTest(){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QGoods qGoods = QGoods.goods;
        JPAQuery<Goods> query = queryFactory.selectFrom(qGoods)
                .where(qGoods.goodsSellStatus.eq(GoodsSellStatus.SELL))
                .where(qGoods.goodsDetail.like("%" + "테스트 상품 상세 설명" + "%"))
                .orderBy(qGoods.goodsPrice.desc());

        List<Goods> goodsList = query.fetch();

        for(Goods goods : goodsList){
            log.info(goods.toString());
        }
    }

}