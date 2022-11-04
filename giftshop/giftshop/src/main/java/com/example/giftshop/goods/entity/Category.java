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
public class Category {

    @Id
    @Column
    private Long categoryId;

    @Column(nullable = false)
    private String categoryName;

    @OneToMany(mappedBy="category")
    private List<GoodsCategory> goodsList = new ArrayList<>();

    @Builder
    public Category(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}
