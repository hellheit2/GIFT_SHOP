package com.example.giftshop.goods.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class ImageVO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int image_id;		// 이미지 아이디

    @Column(name="goods_id")
    private int goods_id;		// 상품 아이디

    private String fileName;	// 파일명
    private String fileType;	// 이미지 구분(상세, 썸네일, 설명)

}
