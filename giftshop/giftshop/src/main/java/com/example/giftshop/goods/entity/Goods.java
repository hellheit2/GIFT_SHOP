package com.example.giftshop.goods.entity;

import com.example.giftshop.goods.constant.GoodsSellStatus;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="t_goods")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
@DynamicInsert
@DynamicUpdate
public class Goods {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long goodsId; // 상품 번호

    @Column(nullable = false, length = 50)
    private String goodsName; // 상품명

    @Column(nullable = false)
    private int goodsPrice; // 가격

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

    private LocalDateTime regTime; // 등록일

/*    @OneToMany(mappedBy="goods", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    private List<GoodsCategory> categoryList = new ArrayList<>(); // 카테고리

    @OneToMany(mappedBy="goods", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    private List<GoodsImage> imageList = new ArrayList<>(); //이미지*/

    @Builder
    public Goods(Long goodsId, String goodsName, int goodsPrice, int goodsSale, String goodsDetail, int goodsView, float goodsRating, GoodsSellStatus goodsSellStatus, LocalDateTime regTime) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsSale = goodsSale;
        this.goodsDetail = goodsDetail;
        this.goodsView = goodsView;
        this.goodsRating = goodsRating;
        this.goodsSellStatus = goodsSellStatus;
        this.regTime = regTime;
    }
}