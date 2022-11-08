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

    private int orderPrice;

    private int count;
}
