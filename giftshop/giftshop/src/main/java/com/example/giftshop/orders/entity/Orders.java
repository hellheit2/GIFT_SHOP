package com.example.giftshop.orders.entity;

import com.example.giftshop.common.entity.BaseEntity;
import com.example.giftshop.orders.constant.OrderStatus;
import com.example.giftshop.member.entity.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Orders extends BaseEntity { //order 정렬시 사용하는 예약어이므로 Orders

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "orders_id")
    private Long id;//주문번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; //고객

    private LocalDateTime orderDate; //주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; //주문상태

    //주인이 아닌 쪽, 주인(OrdersGoods)의 orders에 의해 관리
    //영속성 전이
    //고아객체 제거
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderGoods> orderGoods = new ArrayList<>();

}