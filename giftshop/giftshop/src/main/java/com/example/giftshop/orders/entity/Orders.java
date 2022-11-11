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

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, //주인 x, 주인(OrdersGoods)의 orders에 의해 관리 //영속성 전이
            orphanRemoval = true, fetch = FetchType.LAZY) //고아객체 제거 //지연 로딩
    private List<OrderGoods> orderGoodsList = new ArrayList<>();

    public void addOrderGoods(OrderGoods orderGoods) { //주문 상품 정보 저장
        orderGoodsList.add(orderGoods);
        orderGoods.setOrders(this); //양방향 참조 세팅
    }

    public static Orders createOrder(Member member, List<OrderGoods> orderGoodsList) { //
        Orders order = new Orders();
        order.setMember(member); //주문자 설정

        for(OrderGoods orderGoods : orderGoodsList) {  //주문 목록
            order.addOrderGoods(orderGoods); //주문 목록 세팅
        }
        order.setOrderStatus(OrderStatus.ORDER); //주문 상태(ORDER)
        order.setOrderDate(LocalDateTime.now()); //주문 시간
        return order;
    }

    public int getTotalPrice() { //총 주문 금액 계산
        int totalPrice = 0;
        for(OrderGoods orderGoods : orderGoodsList){
            totalPrice += orderGoods.getTotalPrice(); //각 상품 주문 총금액
        }
        return totalPrice;
    }

    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCEL; //주문 상태 변경(CANCEL)
        for (OrderGoods orderGoods : orderGoodsList) { //각 상품 주문 취소
            orderGoods.cancel(); //재고 원상복구
        }
    }
}