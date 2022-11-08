package com.example.giftshop.goods.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class GoodsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("상품 등록 페이지 권한 테스트")
    @WithMockUser(username = "admin", roles = "ADMIN") //admin 회원, ADMIN 권한
    public void goodsRegisterFormTest() throws  Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/goods/register"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("상품 등록 페이지 일반 권한 접근 테스트")
    @WithMockUser(username = "user", roles = "USER") //user 회원, USER 권한
    public void goodsRegisterNotAdminTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/goods/register"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
