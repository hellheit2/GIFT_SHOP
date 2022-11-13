package com.example.giftshop.orders.service;

import com.example.giftshop.orders.dto.OrderDTO;
import com.example.giftshop.orders.dto.OrderHistoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    public Long order(OrderDTO orderDTO, String email);
    public Page<OrderHistoryDTO> getOrderList(String email, Pageable pageable);
    public boolean validateOrder(Long orderId, String email);
    public void cancelOrder(Long orderId);
    public Long orderByCart(List<OrderDTO> orderDTOList, String email);
}
