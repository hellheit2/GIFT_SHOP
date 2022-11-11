package com.example.giftshop.goods.dto;

import com.example.giftshop.goods.entity.GoodsImage;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsImageDTO {

    private Long id;		// 이미지 아이디
    private String originName;  // 원본 파일명
    private String imgName;	// 파일명
    private String imgUrl; // 이미지 조회 경로
    private String repImgYn; // 대표 이미지 여부
    private String imgType;	// 이미지 구분(상세, 썸네일, 설명)

    // 서로 다른 클래스의 값을 필드명, 자료형이 같을 경우
    // Getter, Setter를 통해 값을 복사
    private static ModelMapper modelMapper = new ModelMapper();

    public static GoodsImageDTO of(GoodsImage goodsImage){
        //이미지 정보를 DTO로 매핑
        return modelMapper.map(goodsImage, GoodsImageDTO.class);
    }

}
