package com.project.novelnet.service;

import com.project.novelnet.repository.NovelRepository;
import com.project.novelnet.repository.UserReopsitory;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

@Service
public class UserService implements UserReopsitory {

    private final NovelRepository novelRepository;
    public UserService(NovelRepository novelRepository) {
        this.novelRepository = novelRepository;
    }


    @Override
    public int IdCheak(String id) {
        int cnt = novelRepository.mailCheak(id);
        return cnt;
    }

    @Override
    public void login() {
//        HttpSession session = null;
//        Model model;
//        if (session.getAttribute("U_NICK")  != null &&
//            session.getAttribute("U_NUM")   != null &&
//            session.getAttribute("U_LEVEL") != null)
//        {
//            model.addAttribute(session.getAttribute("U_NICK"));
//            model.addAttribute(session.getAttribute("U_NUM"));
//            model.addAttribute(session.getAttribute("U_LEVEL"));
//
//            System.out.println(session.getAttribute("U_NICK") + "/" +
//                                session.getAttribute("U_NUM") + "/" +
//                                session.getAttribute("U_LEVEL"));
//        }
//        else
//        {
//            System.out.println("로그인정보 없음");
//        }
    }

}
