package com.example.giftshop.goods.service;

import com.example.giftshop.common.service.FileService;
import com.example.giftshop.goods.entity.GoodsImage;
import com.example.giftshop.goods.repository.GoodsImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class GoodsImageServiceImpl implements GoodsImageService {

    @Value("${goodsImgLocation}") //application.properties 등록 경로
    private String goodsImgLocation;

    private final GoodsImageRepository goodsImageRepository;

    private final FileService fileService;

    @Override
    public void saveGoodsImage(GoodsImage goodsImage, MultipartFile goodsImgFile) throws Exception {
        String originalName = goodsImgFile.getOriginalFilename();
        String imageName = "";
        String imageUrl = "";

        //파일 업로드
        if(!StringUtils.isEmpty(originalName)){
            imageName = fileService.uploadFile(goodsImgLocation,originalName,goodsImgFile.getBytes());
            imageUrl = "/images/goods/" + imageName;
        }

        //상품 이미지 정보 저장
        goodsImage.updateGoodsImg(originalName,imageName,imageUrl);
        goodsImageRepository.save(goodsImage);
    }


    @Override
    public void updateGoodsImg(Long goodsImgId, MultipartFile goodsImage) throws Exception {
        if(!goodsImage.isEmpty()){
            GoodsImage savedGoodsImg = goodsImageRepository.findById(goodsImgId) //이미지 선택
                    .orElseThrow(EntityNotFoundException::new);
            //기존 이미지 파일 삭제
            if(!StringUtils.isEmpty(savedGoodsImg.getImgName())){
                System.out.println("savedImg : " + savedGoodsImg.toString());
                fileService.deleteFile(goodsImgLocation + "/" + savedGoodsImg.getImgName());
            }

            //변경 사항 저장
            String originalName = goodsImage.getOriginalFilename(); //원본 파일 명
            String imageName = fileService.uploadFile(goodsImgLocation, originalName, goodsImage.getBytes()); //생성 파일 명
            String imageUrl = "/images/goods/" + imageName; //파일 url 주소
            savedGoodsImg.updateGoodsImg(originalName,imageName,imageUrl); //GoodsImage 엔티티 메서드
        }

    }
}
