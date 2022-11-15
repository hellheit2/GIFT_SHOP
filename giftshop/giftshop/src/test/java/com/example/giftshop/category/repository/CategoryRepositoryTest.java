package com.example.giftshop.category.repository;



import com.example.giftshop.category.entity.Category;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Slf4j
public class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("카테고리 추가 테스트")
    public void createCategoryTest(){



    }

}
