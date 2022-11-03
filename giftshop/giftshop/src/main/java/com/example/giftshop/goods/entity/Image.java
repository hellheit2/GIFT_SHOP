package com.example.giftshop.goods.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Image {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int image_id;		// 이미지 아이디

    @Column(name="goods_id")
    private int goods_id;		// 상품 아이디

    private String fileName;	// 파일명
    private String fileType;	// 이미지 구분(상세, 썸네일, 설명)

    @OneToMany(mappedBy="image")
    private List<GoodsImage> goods_list = new ArrayList<>();
}
