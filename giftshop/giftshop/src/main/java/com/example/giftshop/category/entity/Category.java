package com.example.giftshop.category.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class Category {

    @Id
    @Column(name = "category_id")
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long categoryId; //카테고리 id

    @Column(nullable = false)
    private String categoryName; //카테고리명

    @Column(name = "depth")
    private Long depth; //카테고리 깊이
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent")
    private Category parent; //상위 카테고리

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Category> children = new ArrayList<>(); //하위카테고리


}
