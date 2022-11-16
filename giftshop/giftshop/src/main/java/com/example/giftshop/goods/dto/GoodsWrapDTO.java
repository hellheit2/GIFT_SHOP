package com.example.giftshop.goods.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsWrapDTO {

    private Long id; //상품 번호

    private String goodsName; //상품명

    private String goodsDetail; //설명

    private String imgUrl; //이미지 주소

    private Integer goodsPrice; //가격

    @QueryProjection
    public GoodsWrapDTO(Long id, String goodsName, String goodsDetail, String imgUrl, Integer goodsPrice) {
        this.id = id;
        this.goodsName = goodsName;
        this.goodsDetail = goodsDetail;
        this.imgUrl = imgUrl;
        this.goodsPrice = goodsPrice;
    }
}
