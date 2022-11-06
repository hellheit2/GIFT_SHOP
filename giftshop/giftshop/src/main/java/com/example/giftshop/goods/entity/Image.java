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
    private int id;		// 이미지 아이디
    private String fileName;	// 파일명
    private String fileType;	// 이미지 구분(상세, 썸네일, 설명)

    @OneToMany(mappedBy="image", cascade = CascadeType.ALL)
    private List<GoodsImage> goodsList = new ArrayList<>();

    @Builder
    public Image(int id, String fileName, String fileType) {
        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
    }
}
