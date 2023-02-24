package com.project.novelnet.repository;

import com.project.novelnet.Vo.MasterVO.MasterMemoVO;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public interface FileUploadRepository {
    public void fileUpload(MultipartFile multipartFile, int n_num);

    public String eventFileUpload(MultipartFile multipartFile);
}
