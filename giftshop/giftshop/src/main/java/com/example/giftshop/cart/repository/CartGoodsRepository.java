package com.example.giftshop.cart.repository;

import com.example.giftshop.cart.dto.CartDetailDTO;
import com.example.giftshop.cart.entity.CartGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartGoodsRepository extends JpaRepository<CartGoods, Long> {

    CartGoods findByCartIdAndGoodsId(Long cartId, Long goodsId); //장바구니 상품 저장 조회

    @Query("select new com.example.giftshop.cart.dto.CartDetailDTO(" + //CartDetailDTO 형태로 정보 저장
            "cg.id, g.goodsName, g.goodsPrice, cg.count, img.imgUrl)" + //장바구니 상품 아이디, 상품명, 상품 가격, 개수, 이미지 경로
            "from CartGoods cg, GoodsImage img " + //장바구니상품과 상품이미지 테이블과
            "join cg.goods g " + //상품 테이블 조인(inner 조인) -> join후 where 절로 필더링
            "where cg.cart.id = :cartId " + //조건 1 : 입력받은 장바구니
            "and img.goods.id = cg.goods.id " + //CartGoods와 GoodsImage의 상품 아이디 일치
            "and img.repImgYn = 'Y' " + //대표 이미지만 필터
            "order by cg.regTime desc") //등록일 내림차순(최근 주문 위로)
    List<CartDetailDTO> findCartDetailDTOList(Long cartId);

    Long countByCartId(Long cartId); //장바구니 상품 개수
}
