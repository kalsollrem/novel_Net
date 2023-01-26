package com.project.novelnet.controller;

import com.google.gson.JsonObject;
import com.project.novelnet.Vo.*;
import com.project.novelnet.repository.NovelMapper;
import com.project.novelnet.repository.NovelRepository;
import com.project.novelnet.repository.WarningMapper;
import com.project.novelnet.service.FileUploadService;
import com.project.novelnet.service.UserService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class TestController {

    private final NovelRepository novelRepository;

    public TestController(NovelRepository novelRepository) {
        this.novelRepository = novelRepository;
    }

    //파일 업로드
    @Autowired
    private FileUploadService fileUploadService;

    //유저서비스
    @Autowired
    private UserService userService;

    @Autowired
    private NovelMapper novelMapper;

    @Autowired
    private WarningMapper warningMapper;

    @GetMapping("/test")
    public String t(){

        //읽은 기록 등록
        int u_num = 1;
        int n_num = 1;
        int m_num = 2;
        String w_why = "1";


        //신고 삭제
        //warningMapper.deleteMemoWarning(n_num,u_num);

        //신고 등록 여부 확인
        String timgDiff = warningMapper.cheakMemoWarning(n_num,u_num);
        System.out.println(timgDiff);

        //신고시간이 null값이거나 1440분을 넘으면 경고등록
        if (timgDiff == null)                         { warningMapper.setMemoWarning(n_num,u_num,m_num,w_why); }
        else{ if(Integer.parseInt(timgDiff) < 1440)   { warningMapper.setMemoWarning(n_num,u_num,m_num,w_why); }  }


        return "test" ;
    }
}
