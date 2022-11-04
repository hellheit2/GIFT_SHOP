package com.example.giftshop.goods.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class GoodsCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "goodsId")
    private Goods goods;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
}
