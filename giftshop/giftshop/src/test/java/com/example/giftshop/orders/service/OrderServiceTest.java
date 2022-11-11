package com.example.giftshop.orders.service;

import com.example.giftshop.goods.constant.GoodsSellStatus;
import com.example.giftshop.goods.entity.Goods;
import com.example.giftshop.goods.repository.GoodsRepository;
import com.example.giftshop.member.dto.MemberFormDTO;
import com.example.giftshop.member.entity.Member;
import com.example.giftshop.member.repository.MemberRepository;
import com.example.giftshop.orders.OrderDTO.OrderDTO;
import com.example.giftshop.orders.constant.OrderStatus;
import com.example.giftshop.orders.entity.OrderGoods;
import com.example.giftshop.orders.entity.Orders;
import com.example.giftshop.orders.repository.OrdersRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    GoodsRepository goodsRepository;

    @Autowired
    MemberRepository memberRepository;

    public Goods saveGoods(){
        Goods goods = Goods.builder()
                .goodsName("테스트 상품")
                .goodsPrice(10000)
                .goodsStock(100)
                .goodsDetail("테스트 상품 설명")
                .goodsSellStatus(GoodsSellStatus.SELL).build();

        return goodsRepository.save(goods);
    }

    public Member saveMember(){
        Member member = new Member();
        member.setEmail("test@test.com");
        return memberRepository.save(member);

    }

    @Test
    @DisplayName("주문 테스트")
    public void orderOne(){
        Goods goods = saveGoods();
        Member member = saveMember();

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCount(10);
        orderDTO.setGoodsId(goods.getId());
        System.out.println("-----------------------" + goods.getGoodsName());
        System.out.println("-----------------------" + member.getEmail());
        Long orderId = orderService.orderOne(orderDTO, member.getEmail());
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);

        List<OrderGoods> orderGoodsList = order.getOrderGoodsList();

        int totalPrice = orderDTO.getCount() * goods.getGoodsPrice();

        assertEquals(totalPrice, order.getTotalPrice());
    }

    @Test
    @DisplayName("주문 취소 테스트")
    public void cancelOrder(){
        Goods item = saveGoods();
        Member member = saveMember();

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCount(10);
        orderDTO.setGoodsId(item.getId());
        Long orderId = orderService.orderOne(orderDTO, member.getEmail());

        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        orderService.cancelOrder(orderId);

        assertEquals(OrderStatus.CANCEL, order.getOrderStatus());
        assertEquals(100, item.getGoodsStock());
    }

}
