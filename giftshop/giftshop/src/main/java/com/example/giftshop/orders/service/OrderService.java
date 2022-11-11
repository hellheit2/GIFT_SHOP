package com.example.giftshop.orders.service;

import com.example.giftshop.orders.OrderDTO.OrderDTO;
import java.util.List;

public interface OrderService {

    public Long orderOne(OrderDTO orderDTO, String email);
    public boolean validateOrder(Long orderId, String email);
    public void cancelOrder(Long orderId);
    public Long orderMany(List<OrderDTO> orderDTOList, String email);
}
