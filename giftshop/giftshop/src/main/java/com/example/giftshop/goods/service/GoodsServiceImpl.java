package com.example.giftshop.goods.service;


import com.example.giftshop.goods.dto.GoodsFormDTO;
import com.example.giftshop.goods.dto.GoodsImageDTO;
import com.example.giftshop.goods.entity.Goods;
import com.example.giftshop.goods.entity.GoodsImage;
import com.example.giftshop.goods.repository.GoodsImageRepository;
import com.example.giftshop.goods.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService{

    private final GoodsRepository goodsRepository;
    private final GoodsImageRepository goodsImageRepository;
    private final GoodsImageService goodsImageService;
    @Override
    public Long uploadGoods(GoodsFormDTO goodsFormDTO, List<MultipartFile> goodsImageList) throws Exception {

        //상품 등록
        Goods goods = goodsFormDTO.createGoods();
        goodsRepository.save(goods);

        //이미지 등록
        for (int i = 0; i < goodsImageList.size(); i++){
            GoodsImage goodsImage = GoodsImage.builder()
                    .goods(goods)
                    .build();

            if(i == 0){
                goodsImage.setRepImgYn("Y");
            }else{
                goodsImage.setRepImgYn("N");
            }
            goodsImageService.saveGoodsImage(goodsImage,goodsImageList.get(i));
        }
        return goods.getId();
    }

    @Override
    @Transactional(readOnly = true) //트랜잭션 읽기 전용
    public GoodsFormDTO getGoodsDetail(Long goodsId) {

        List<GoodsImage> goodsImageList = goodsImageRepository.findByGoodsIdOrderByIdAsc(goodsId); //상품 아아디로 이미지 리스트
        List<GoodsImageDTO> goodsImgDTOList = new ArrayList<>();
        for(GoodsImage temp : goodsImageList){ //이미지 리스트 DTO 객체로 저장
            GoodsImageDTO goodsImgDTO = GoodsImageDTO.of(temp); //필드명, 자료형으로 내용 복사
            goodsImgDTOList.add(goodsImgDTO); //이미지DTO 리스트로 저장
        }

        Goods goods = goodsRepository.findById(goodsId) //상품 검색
                .orElseThrow(EntityNotFoundException::new); //없을 경우 exception
        GoodsFormDTO goodsFormDTO = GoodsFormDTO.of(goods); //상품 DTO 객체로 저장
        goodsFormDTO.setGoodsImageList(goodsImgDTOList); //상품 DTO에 이미지DTO 리스트 세팅

        return goodsFormDTO; //상품DTO 전달
    }

    @Override
    public Long updateGoods(GoodsFormDTO goodsFormDTO, List<MultipartFile> goodsImageList) throws Exception {

        //상품 수정
        Goods goods = goodsRepository.findById(goodsFormDTO.getId()) // 상품 확인
                .orElseThrow(EntityNotFoundException::new);
        goods.updateGoods(goodsFormDTO); //DTO 토대로 상품 정보 수정

        List<Long> goodsImageIds = goodsFormDTO.getGoodsImageIds(); //이미지 아이디 리스트

        System.out.println(goodsImageList.size());
        for (int i = 0; i < goodsImageList.size(); i++) {
            goodsImageService.updateGoodsImg(goodsImageIds.get(i), goodsImageList.get(i)); //이미지 업데이트
        }
        return goods.getId();
    }

}
