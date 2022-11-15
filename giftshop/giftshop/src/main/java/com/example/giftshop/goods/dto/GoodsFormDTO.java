package com.example.giftshop.goods.dto;

import com.example.giftshop.goods.constant.GoodsSellStatus;
import com.example.giftshop.goods.entity.Goods;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsFormDTO {

    private Long id;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String goodsName;

    @NotNull(message = "상품가격은 필수 입력 값입니다.")
    private Integer goodsPrice;

    @NotBlank(message = "상품설명은 필수 입력 값입니다.")
    private String goodsDetail;

    @NotNull(message = "상품재고는 필수 입력 값입니다.")
    private Integer goodsStock;

    private GoodsSellStatus goodsSellStatus; // 판매 상태

    private List<GoodsImageDTO> goodsImageList = new ArrayList<>(); // 이미지 정보 저장
    private List<Long> goodsImageIds = new ArrayList<>(); // 이미지 아이디 저장

    private static ModelMapper modelMapper = new ModelMapper();

    public Goods createGoods(){
        return modelMapper.map(this, Goods.class);
    }

    public static GoodsFormDTO of(Goods goods){
        return modelMapper.map(goods,GoodsFormDTO.class);
    }
}
