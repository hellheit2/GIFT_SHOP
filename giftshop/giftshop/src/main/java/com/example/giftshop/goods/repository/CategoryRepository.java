package com.example.giftshop.goods.repository;

import com.example.giftshop.goods.entity.Category;
import com.example.giftshop.goods.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByCategoryName(String categoryName);
}
