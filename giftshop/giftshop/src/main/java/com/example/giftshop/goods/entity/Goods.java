package com.example.giftshop.goods.entity;

import com.example.giftshop.common.entity.BaseEntity;
import com.example.giftshop.goods.constant.GoodsSellStatus;
import com.example.giftshop.goods.dto.GoodsFormDTO;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@DynamicInsert
@DynamicUpdate
public class Goods extends BaseEntity {

    @Id
    @Column(name = "goods_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 상품 번호

    @Column(nullable = false, length = 50)
    private String goodsName; // 상품명

    @Column(nullable = false)
    private int goodsPrice; // 가격

    @Column(nullable = false)
    private int goodsStock; // 재고수량

    @Column(nullable = false)
    private int goodsSale; // 할인율

    @Lob
    @Column(nullable = false)
    private String goodsDetail; // 설명

    @Column(columnDefinition = "int default 0")
    private int goodsView;	// 조회수

    @Column(columnDefinition = "float default 0.0")
    private float goodsRating; // 평점

    @Enumerated(EnumType.STRING)
    private GoodsSellStatus goodsSellStatus; // 판매 상태

    @OneToMany(mappedBy="goods", cascade = CascadeType.ALL) //, fetch=FetchType.EAGER
    private List<GoodsCategory> categoryList = new ArrayList<>(); // 카테고리

    //상품 데이터 업데이트
    //엔티티 클래스 비즈니스 로직 -> 객체지향적 코딩
    public void updateGoods(GoodsFormDTO goodsFormDTO){
        this.goodsName = goodsFormDTO.getGoodsName();
        this.goodsPrice = goodsFormDTO.getGoodsPrice();
        this.goodsStock = goodsFormDTO.getGoodsStock();
        this.goodsDetail = goodsFormDTO.getGoodsDetail();
        this.goodsSellStatus = goodsFormDTO.getGoodsSellStatus();


    }
}
