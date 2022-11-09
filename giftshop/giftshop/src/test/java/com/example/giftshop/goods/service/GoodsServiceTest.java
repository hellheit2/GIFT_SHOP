package com.example.giftshop.goods.service;

import com.example.giftshop.goods.constant.GoodsSellStatus;
import com.example.giftshop.goods.dto.GoodsFormDTO;
import com.example.giftshop.goods.entity.Goods;
import com.example.giftshop.goods.entity.GoodsImage;
import com.example.giftshop.goods.repository.GoodsImageRepository;
import com.example.giftshop.goods.repository.GoodsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class GoodsServiceTest {

    @Autowired
    GoodsService goodsService;
    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    GoodsImageRepository goodsImageRepository;

    List<MultipartFile> createMultipartFiles() throws Exception{
        List<MultipartFile> multipartFileList = new ArrayList<>();

        for(int i = 0; i < 5; i++){
            String path = "C:/giftshop/goods/";
            String imageName = "image" + i + ".jpg";
            MockMultipartFile multipartFile = new MockMultipartFile(path,imageName,"image/jpg",new byte[]{1,2,3,4});
            multipartFileList.add(multipartFile);
        }

        return multipartFileList;
    }

    @Test
    @DisplayName("상품 등록 테스트")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void uploadItem() throws Exception{
        GoodsFormDTO goodsFormDTO = GoodsFormDTO.builder()
                .goodsName("테스트 상품")
                .goodsSellStatus(GoodsSellStatus.SELL)
                .goodsDetail("상품 설명")
                .goodsPrice(10000)
                .goodsStock(100)
                .build();

        List<MultipartFile> multipartFileList = createMultipartFiles();
        Long goodsId = goodsService.uploadGoods(goodsFormDTO, multipartFileList);

        List<GoodsImage> goodsImageList = goodsImageRepository.findByGoodsIdOrderByIdAsc(goodsId);
        Goods goods = goodsRepository.findById(goodsId)
                .orElseThrow(EntityNotFoundException::new);

        assertEquals(goodsFormDTO.getGoodsName(), goods.getGoodsName());

    }
}
