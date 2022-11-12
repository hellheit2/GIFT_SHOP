package com.example.giftshop.orders.service;

import com.example.giftshop.goods.entity.Goods;
import com.example.giftshop.goods.entity.GoodsImage;
import com.example.giftshop.goods.repository.GoodsImageRepository;
import com.example.giftshop.goods.repository.GoodsRepository;
import com.example.giftshop.member.entity.Member;
import com.example.giftshop.member.repository.MemberRepository;
import com.example.giftshop.orders.OrderDTO.OrderDTO;
import com.example.giftshop.orders.OrderDTO.OrderGoodsDTO;
import com.example.giftshop.orders.OrderDTO.OrderHistoryDTO;
import com.example.giftshop.orders.entity.OrderGoods;
import com.example.giftshop.orders.entity.Orders;
import com.example.giftshop.orders.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final GoodsRepository goodsRepository;
    private final MemberRepository memberRepository;
    private final OrdersRepository ordersRepository;

    private final GoodsImageRepository goodsImgRepository;

    public Long order(OrderDTO orderDTO, String email){
        //주문 1개
        Goods goods = goodsRepository.findById(orderDTO.getGoodsId()) //주문 상품 조회
                .orElseThrow(EntityNotFoundException::new);

        Member member = memberRepository.findByEmail(email); //로그인 회원 정보

        List<OrderGoods> orderGoodsList = new ArrayList<>(); //주문상품 목록
        OrderGoods orderGoods = OrderGoods.createOrderGoods(goods, orderDTO.getCount()); //상품, 수량 => 주문상품 엔티티
        orderGoodsList.add(orderGoods); //주문 상품 목록 저장
        Orders order = Orders.createOrder(member, orderGoodsList); //주문 엔티티 생성
        ordersRepository.save(order);

        return order.getId();
    }

    @Transactional(readOnly = true)
    public Page<OrderHistoryDTO> getOrderList(String email, Pageable pageable) {

        List<Orders> orders = ordersRepository.findOrders(email, pageable); //사용자 주문 조회(페이징)
        Long totalCount = ordersRepository.countOrder(email); //주문 총 개수

        List<OrderHistoryDTO> orderHistoryDTOList = new ArrayList<>(); //주문 이력 리스트

        for (Orders order : orders) { //사용자 주문 순회
            OrderHistoryDTO orderHistoryDTO = new OrderHistoryDTO(order); //주문 이력
            
            List<OrderGoods> orderGoodsList = order.getOrderGoodsList(); //주문상품 리스트
            
            for (OrderGoods orderGoods : orderGoodsList) { //주문상품 순회
                GoodsImage goodsImage = goodsImgRepository
                        .findByGoodsIdAndRepImgYn(orderGoods.getGoods().getId(), "Y"); //대표 이미지 조회
                OrderGoodsDTO orderGoodsDTO =
                        new OrderGoodsDTO(orderGoods, goodsImage.getImgUrl()); //주문상품 DTO 생성
                orderHistoryDTO.addOrderItemDto(orderGoodsDTO); //주문 이력에 추가
            }
            orderHistoryDTOList.add(orderHistoryDTO); //주문이력 리스트에 추가
        }

        return new PageImpl<OrderHistoryDTO>(orderHistoryDTOList, pageable, totalCount); //주문 이력 페이징
    }

    @Transactional(readOnly = true)
    public boolean validateOrder(Long orderId, String email){
        Member currentMember = memberRepository.findByEmail(email); //로그인 회원 조회
        Orders order = ordersRepository.findById(orderId) //주문 조회
                .orElseThrow(EntityNotFoundException::new);
        Member savedMember = order.getMember(); //주문자 조회

        if(!StringUtils.equals(currentMember.getEmail(), savedMember.getEmail())){ //주문자 일치 확인
            return false;
        }
        return true;
    }

    public void cancelOrder(Long orderId){
        Orders order = ordersRepository.findById(orderId) //주문 조회
                .orElseThrow(EntityNotFoundException::new);
        order.cancelOrder(); //주문 취소(주문상태 변경, 재고 복구)
    }

    public Long orderMany(List<OrderDTO> orderDTOList, String email){

        Member member = memberRepository.findByEmail(email);
        List<OrderGoods> orderGoodsList = new ArrayList<>();

        for (OrderDTO orderDTO : orderDTOList) {
            Goods goods = goodsRepository.findById(orderDTO.getGoodsId())
                    .orElseThrow(EntityNotFoundException::new);

            OrderGoods orderItem = OrderGoods.createOrderGoods(goods, orderDTO.getCount());
            orderGoodsList.add(orderItem);
        }

        Orders order = Orders.createOrder(member, orderGoodsList);
        ordersRepository.save(order);

        return order.getId();
    }
}