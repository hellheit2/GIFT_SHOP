package com.example.giftshop.goods.entity;

import com.example.giftshop.goods.constant.GoodsSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="t_goods")
@Getter
@Setter
@ToString
@DynamicInsert
@DynamicUpdate
public class GoodsVO {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long goods_id; // 상품 번호

    @Column(nullable = false, length = 50)
    private String goods_name; // 상품명

    @Column(nullable = false)
    private int goods_price; // 가격

    @Column(nullable = false)
    private int goods_sale; // 할인율

    @Lob
    @Column(nullable = false)
    private String goods_detail; // 설명

    @Column(columnDefinition = "int default 0")
    private int goods_view;	// 조회수

    @Column(columnDefinition = "float default 0.0")
    private float goods_rating; // 평점

    @Enumerated(EnumType.STRING)
    private GoodsSellStatus sellStatus; // 판매 상태

    private LocalDateTime goods_regTime; // 등록일

    @Column(nullable = false)
    private int goods_category; // 카테고리

    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="goods_id")
    private List<ImageVO> goods_images; //이미지

}
