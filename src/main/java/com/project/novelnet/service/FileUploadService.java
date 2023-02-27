package com.project.novelnet.service;

import com.project.novelnet.Vo.MasterVO.MasterMemoVO;
import com.project.novelnet.repository.FileUploadRepository;
import com.project.novelnet.repository.MasterMapper;
import com.project.novelnet.repository.NovelMapper;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FileUploadService implements FileUploadRepository {

    @Autowired
    private NovelMapper novelMapper;

    @Autowired
    MasterMapper masterMapper;

    String root = "C:\\code\\KimJeonghyun\\novelNet\\src\\main\\resources\\static\\noteImg\\";
    @Override
    public void fileUpload(MultipartFile multipartFile, int n_num) {
        System.out.println("첨부파일명 "+multipartFile.getOriginalFilename());

        String fileRoot         = "C:\\code\\KimJeonghyun\\novelNet\\src\\main\\resources\\static\\noteImg\\";	  //저장될 외부 파일 경로
        String originalFileName = multipartFile.getOriginalFilename();	                                          //오리지날 파일명
        String extension        = originalFileName.substring(originalFileName.lastIndexOf("."));	          //파일 확장자

        String savedFileName    = UUID.randomUUID() + extension;	                                              //저장될 파일 명

        File targetFile         = new File(fileRoot + savedFileName);

        try {
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);	//파일 저장
            System.out.println("저장 성공");
            novelMapper.updateCover(savedFileName,n_num);

        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile);	//저장된 파일 삭제
            e.printStackTrace();
            System.out.println("저장 실패");
        }
    }

    @Override
    public String eventFileUpload(MultipartFile multipartFile) {
        System.out.println("첨부파일명 "+multipartFile.getOriginalFilename());
        String answer = "no";

        String fileRoot         = "C:\\code\\KimJeonghyun\\novelNet\\src\\main\\resources\\static\\noteImg\\";	  //저장될 외부 파일 경로
        String originalFileName = multipartFile.getOriginalFilename();	                                          //오리지날 파일명
        String extension        = originalFileName.substring(originalFileName.lastIndexOf("."));	          //파일 확장자

        String savedFileName    = UUID.randomUUID() + extension;	                                              //저장될 파일 명

        File targetFile         = new File(fileRoot + savedFileName);

        try {
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);	//파일 저장
            System.out.println("저장 성공");
            answer = savedFileName;

        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile);	//저장된 파일 삭제
            e.printStackTrace();
            System.out.println("저장 실패");
        }

        return answer;
    }

    @Override
    public String  ProfillUpload(MultipartFile multipartFile) {
        System.out.println("첨부파일명 "+multipartFile.getOriginalFilename());

        String answer = "no";
        String fileRoot         = "C:\\code\\KimJeonghyun\\novelNet\\src\\main\\resources\\static\\userImg\\";	  //저장될 외부 파일 경로
        String originalFileName = multipartFile.getOriginalFilename();	                                          //오리지날 파일명
        String extension        = originalFileName.substring(originalFileName.lastIndexOf("."));	          //파일 확장자

        String savedFileName    = UUID.randomUUID() + extension;	                                              //저장될 파일 명

        File targetFile         = new File(fileRoot + savedFileName);

        try {
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);	//파일 저장
            System.out.println("저장 성공");
            answer = savedFileName;


        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile);	//저장된 파일 삭제
            e.printStackTrace();
            System.out.println("저장 실패");
        }

        return answer;
    }

    @Override
    public void deleteFile(String fileName) {
        // 파일의 경로 + 파일명
        String filePath = root+fileName;

        File deleteFile = new File(filePath);

        // 파일이 존재하는지 체크 존재할경우 true, 존재하지않을경우 false
        if(deleteFile.exists()) {
            // 파일을 삭제합니다.
            deleteFile.delete();
            System.out.println("파일을 삭제하였습니다.");

        } else {
            System.out.println("파일이 존재하지 않습니다.");
        }
    }


    @Override
    public void deleteProfill(String fileName) {
        // 파일의 경로 + 파일명
        String filePath = "C:\\code\\KimJeonghyun\\novelNet\\src\\main\\resources\\static\\userImg\\"+fileName;

        File deleteFile = new File(filePath);

        // 파일이 존재하는지 체크 존재할경우 true, 존재하지않을경우 false
        if(deleteFile.exists()) {
            // 파일을 삭제합니다.
            deleteFile.delete();
            System.out.println("파일을 삭제하였습니다.");

        } else {
            System.out.println("파일이 존재하지 않습니다.");
        }
    }
}
