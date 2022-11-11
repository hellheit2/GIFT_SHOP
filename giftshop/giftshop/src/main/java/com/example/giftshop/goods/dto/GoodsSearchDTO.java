package com.example.giftshop.goods.dto;

import com.example.giftshop.goods.constant.GoodsSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsSearchDTO {

    private String searchDateType; //등록일 기준
    
    private GoodsSellStatus searchSellStatus; //판매 상태

    private String searchBy; //검색 유형

    private String searchQuery = ""; //키워드

}
