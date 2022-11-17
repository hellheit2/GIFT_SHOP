package com.example.giftshop.orders.service;

import com.example.giftshop.orders.dto.OrderDTO;
import com.example.giftshop.orders.dto.OrderHistoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    Long order(OrderDTO orderDTO, String email);
    Page<OrderHistoryDTO> getOrderList(String email, Pageable pageable);
    boolean validateOrder(Long orderId, String email);
    void cancelOrder(Long orderId);
    Long orderByCart(List<OrderDTO> orderDTOList, String email);
}
