package com.example.giftshop.category.repository;

import com.example.giftshop.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByCategoryName(String categoryName);

    Boolean existsCategoryByCategoryName(String categoryName);

}
