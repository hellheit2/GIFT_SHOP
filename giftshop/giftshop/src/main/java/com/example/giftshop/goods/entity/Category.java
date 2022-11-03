package com.example.giftshop.goods.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Category {

    @Id
    @Column
    private Long category_id;

    @Column(nullable = false)
    private String category_name;

    @OneToMany(mappedBy="category")
    private List<GoodsCategory> goods_list = new ArrayList<>();


}
