package com.project.novelnet.controller;

import com.google.gson.JsonObject;
import com.project.novelnet.Vo.*;
import com.project.novelnet.repository.NovelMapper;
import com.project.novelnet.repository.NovelRepository;
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


    @GetMapping("/test")
    public String t(){

            //읽은 기록 등록
            String u_num = "1";
            String n_num = "2";
            String m_num = "1";
            novelMapper.setRecord(n_num,m_num,u_num);

            return "test" ;
    }
}
