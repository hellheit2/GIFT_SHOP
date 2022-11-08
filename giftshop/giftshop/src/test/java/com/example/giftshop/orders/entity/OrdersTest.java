package com.example.giftshop.orders.entity;


import com.example.giftshop.goods.constant.GoodsSellStatus;
import com.example.giftshop.goods.entity.Goods;
import com.example.giftshop.goods.repository.GoodsRepository;
import com.example.giftshop.member.entity.Member;
import com.example.giftshop.member.repository.MemberRepository;
import com.example.giftshop.orders.repository.OrderGoodsRepository;
import com.example.giftshop.orders.repository.OrdersRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
@Transactional
@Slf4j
public class OrdersTest {

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    GoodsRepository goodsRepository;

    @PersistenceContext
    EntityManager em;

    public Goods createGoods(){
        Goods goods = Goods.builder()
                .goodsName("테스트 상품")
                .goodsPrice(10000)
                .goodsStock(100)
                .goodsDetail("테스트 상품 설명")
                .goodsSellStatus(GoodsSellStatus.SELL).build();

        return goods;
    }

    @Test
    @DisplayName("영속성 전이 테스트")
    public void cascadeTest(){

        Orders orders = new Orders(); //주문 생성

        for(int i = 0; i < 3; i++){
            Goods goods = this.createGoods(); //상품 생성
            goodsRepository.save(goods); //상품 등록
            OrderGoods orderGoods = new OrderGoods(); //상품주문 생성
            orderGoods.setGoods(goods); //상품주문에 상품 등록
            orderGoods.setCount(10); //개수 설정
            orderGoods.setOrderPrice(1000); //가격 설정
            orderGoods.setOrders(orders); //주문 지정(주인)
            orders.getOrderGoods().add(orderGoods); //주문에 상품주문 추가
        }

        ordersRepository.saveAndFlush(orders); //orders 저장, flush 호출 => orders에 속한 ordersGoods에 영속성 전이
        em.clear(); //영속성 컨텍스트 초기화

        Orders savedOrders = ordersRepository.findById(orders.getId())
                .orElseThrow(EntityNotFoundException::new);
        assertEquals(3,savedOrders.getOrderGoods().size());
    }

    @Autowired
    MemberRepository memberRepository;

    public Orders createOrders(){
        Orders orders = new Orders(); //주문 생성

        for(int i = 0; i < 3; i++){
            Goods goods = this.createGoods(); //상품 생성
            goodsRepository.save(goods); //상품 등록
            OrderGoods ordersGoods = new OrderGoods(); //상품주문 생성
            ordersGoods.setGoods(goods); //상품주문에 상품 등록
            ordersGoods.setCount(10); //개수 설정
            ordersGoods.setOrderPrice(1000); //가격 설정
            ordersGoods.setOrders(orders); //주문 지정(주인)
            orders.getOrderGoods().add(ordersGoods); //주문에 상품주문 추가
        }

        Member member = new Member();
        memberRepository.save(member);

        orders.setMember(member);
        ordersRepository.save(orders);
        return orders;
    }

    @Test
    @DisplayName("고아객체 제거 테스트")
    public void orphanRemovalTest(){
        Orders orders = this.createOrders();
        orders.getOrderGoods().remove(0);
        em.flush();
    }

    @Autowired
    OrderGoodsRepository orderGoodsRepository;

    @Test
    @DisplayName("지연 로딩 테스트")
    public void lazyLoadingTest(){
        Orders orders = this.createOrders();
        Long ordersGoodsId = orders.getOrderGoods().get(0).getId();
        em.flush();
        em.clear();

        OrderGoods orderGoods = orderGoodsRepository.findById(ordersGoodsId)
                .orElseThrow(EntityNotFoundException::new);
        log.info("Order class : " + orderGoods.getOrders().getClass());
        log.info("==================================================");
        orderGoods.getOrders().getOrderDate();
        log.info("==================================================");


        
    }

}
