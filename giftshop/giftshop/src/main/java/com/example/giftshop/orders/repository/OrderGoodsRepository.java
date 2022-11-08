package com.example.giftshop.orders.repository;

import com.example.giftshop.orders.entity.OrderGoods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderGoodsRepository extends JpaRepository<OrderGoods, Long> {
}
