package com.example.giftshop.orders.entity;

import com.example.giftshop.common.entity.BaseEntity;
import com.example.giftshop.goods.entity.Goods;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class OrderGoods extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "order_goods_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩 방식
    @JoinColumn(name = "goods_id")
    private Goods goods;

    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩 방식
    @JoinColumn(name = "orders_id")
    private Orders orders;

    private int orderPrice; //상품가격

    private int count; //수량

    public static OrderGoods createOrderGoods(Goods goods, int count){
        OrderGoods orderGoods = new OrderGoods();
        orderGoods.setGoods(goods); //주문 상품
        orderGoods.setCount(count); //주문 수량
        orderGoods.setOrderPrice(goods.getGoodsPrice()); //주문 가격
        goods.removeStock(count); //재고 감소
        return orderGoods;
    }

    public int getTotalPrice(){ //상품 가격 * 개수
        return orderPrice * count;
    }

    public void cancel() { //주문 취소
        this.getGoods().addStock(count); //재고 복구
    }
}
