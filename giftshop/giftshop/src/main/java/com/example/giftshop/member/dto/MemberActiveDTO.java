package com.example.giftshop.member.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberActiveDTO {
    //회원 활동
    private Long cart; //장바구니 상품 수
    private Long total; //주문 수
    private Long canceled; //주문 취소 건수

}
