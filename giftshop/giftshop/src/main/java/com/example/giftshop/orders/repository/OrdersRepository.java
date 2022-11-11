package com.example.giftshop.orders.repository;

import com.example.giftshop.orders.entity.Orders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders,Long> {

    @Query("select o from Orders o " +
            "where o.member.email = :email " +
            "order by o.orderDate desc")
    List<Orders> findOrders(@Param("email") String email, Pageable pageable); //주문 조회

    @Query("select count(o) from Orders o " +
            "where o.member.email = :email")
    Long countOrder(@Param("email") String email); //주문 총 개수
}
