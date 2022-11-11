package com.example.giftshop.goods.entity;

import com.example.giftshop.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class GoodsImage extends BaseEntity {

    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;		// 이미지 아이디
    private String originName;  // 원본 파일명
    private String imgName;	// 파일명
    private String imgUrl; // 이미지 조회 경로
    private String repImgYn; // 대표 이미지 여부
    private String imgType;	// 이미지 구분(상세, 썸네일, 설명)

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    public void updateGoodsImg(String originName, String imgName, String imgUrl){
        this.originName = originName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}
