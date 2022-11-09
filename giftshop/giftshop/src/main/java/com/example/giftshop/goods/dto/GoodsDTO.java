package com.example.giftshop.goods.dto;

import com.example.giftshop.goods.constant.GoodsSellStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsDTO {

    private Long goodsId; // 상품 번호

    private String goodsName; // 상품명

    private int goodsPrice; // 가격

    private int goodsSale; // 할인율

    private String goodsDetail; // 설명

    private int goodsView;	// 조회수

    private float goodsRating; // 평점

    private GoodsSellStatus goodsSellStatus; // 판매 상태

    private LocalDateTime regTime; // 등록일

}
