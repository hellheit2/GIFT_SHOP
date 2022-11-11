package com.example.giftshop.orders.OrderDTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderDTO {

    @NotNull(message = "상품 아이디는 필수 입력 값입니다.")
    private Long goodsId; //상품 아이디

    @Min(value = 1, message = "최소 주문 수량은 1개 입니다.") //최소 지정
    @Max(value = 999, message = "최대 주문 수량은 999개 입니다.") //최대 지정
    private int count; //주문 수량
}
