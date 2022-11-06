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
    private Long id;

    @Column(nullable = false)
    private String categoryName;

    @OneToMany(mappedBy="category", cascade = CascadeType.ALL)
    private List<GoodsCategory> goodsList = new ArrayList<>();

    @Builder
    public Category(Long id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }
}
