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
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imageId;		// 이미지 아이디
    private String fileName;	// 파일명
    private String fileType;	// 이미지 구분(상세, 썸네일, 설명)

    @OneToMany(mappedBy="image")
    private List<GoodsImage> goodsList = new ArrayList<>();

    @Builder
    public Image(int imageId, String fileName, String fileType) {
        this.imageId = imageId;
        this.fileName = fileName;
        this.fileType = fileType;
    }
}
