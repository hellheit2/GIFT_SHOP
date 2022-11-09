package com.example.giftshop.common.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Slf4j
public class FileServiceImpl implements FileService{

    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception{

        UUID uuid = UUID.randomUUID(); //파일명 중복 방지
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); //파일 확장자
        String savedFileName = uuid.toString() + extension; //저장 파일명 [uuid].[확장자]
        String fileUploadFullUrl = uploadPath + "/" + savedFileName; //저장 위치
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl); //파일 출력 스트림 생성
        fos.write(fileData); //파일 출력 스트림 입력
        fos.close(); //파일 출력 스트림 close

        return savedFileName; //업로드 파일 이름 반환
    }

    public void deleteFile(String filePath) throws Exception{

        File deleteFile = new File(filePath);

        if(deleteFile.exists()){
            deleteFile.delete();
            log.info("파일 삭제 완료");
        }else{
            log.info("파일 탐색 불가");
        }
    }
}
