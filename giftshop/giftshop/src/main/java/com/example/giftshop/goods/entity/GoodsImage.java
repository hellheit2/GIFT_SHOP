package com.example.giftshop.goods.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class GoodsImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "goodsId")
    private Goods goods;

    @ManyToOne
    @JoinColumn(name = "imageId")
    private Image image;
}
