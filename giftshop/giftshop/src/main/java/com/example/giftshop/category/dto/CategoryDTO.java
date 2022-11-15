package com.example.giftshop.category.dto;

import com.example.giftshop.category.entity.Category;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    private Long categoryId; //카테고리 id
    private String categoryName; //카테고리명
    private Long depth; //카테고리 깊이
    private Category parent; //상위 카테고리
    private List<CategoryDTO> children; //하위 카테고리

    // 서로 다른 클래스의 값을 필드명, 자료형이 같을 경우
    // Getter, Setter를 통해 값을 복사
    private static ModelMapper modelMapper = new ModelMapper();

    public static CategoryDTO of(Category category){
        //카테고리 정보를 DTO로 매핑
        return modelMapper.map(category, CategoryDTO.class);
    }
}
