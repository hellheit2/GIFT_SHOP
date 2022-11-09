package com.example.giftshop.goods.dto;

import com.example.giftshop.goods.constant.GoodsSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsSearchDTO {

    private GoodsSellStatus searchSellstatus;

    private String searchBy;

    private String searchQuery = "";

}
