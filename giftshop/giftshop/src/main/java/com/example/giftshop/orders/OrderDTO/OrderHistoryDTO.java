package com.example.giftshop.orders.OrderDTO;

import com.example.giftshop.orders.constant.OrderStatus;
import com.example.giftshop.orders.entity.Orders;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderHistoryDTO {

    private Long orderId; //주문 번호
    private String orderDate; //주문날짜
    private OrderStatus orderStatus; //주문 상태

    private List<OrderGoodsDTO> orderGoodsDTOList = new ArrayList<>(); //주문 리스트

    public OrderHistoryDTO(Orders order){
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.orderStatus = order.getOrderStatus();
    }

    //주문 상품리스트
    public void addOrderItemDto(OrderGoodsDTO orderGoodsDTO){ //주문 상품 리스트에 항목 추가
        orderGoodsDTOList.add(orderGoodsDTO);
    }
}
