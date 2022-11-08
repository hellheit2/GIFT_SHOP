package com.example.giftshop.goods.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class Image {

    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;		// 이미지 아이디
    private String fileName;	// 파일명
    private String fileType;	// 이미지 구분(상세, 썸네일, 설명)

/*    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id",nullable = false)
    private Goods goods;*/

    @Builder
    public Image(int id, String fileName, String fileType) {
        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
    }
}
