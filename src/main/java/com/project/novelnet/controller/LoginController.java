package com.project.novelnet.controller;

import com.project.novelnet.Vo.UserVO;
import com.project.novelnet.repository.NovelMapper;
import com.project.novelnet.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class LoginController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NovelMapper novelMapper;

    private UserVO userVO ;

    @PostMapping("/login.do")
    @ResponseBody
    public String post(@RequestParam String u_mail, String u_pass, HttpServletRequest request, HttpSession session)throws Exception{
        System.out.println("ID : " + u_mail +" || "+ "PW : " +u_pass);

        //Login_Class
        userVO = userMapper.getUserData(u_mail, u_pass);

        if(userVO == null)
        {
            System.out.println("Login Failed" );
            return "loginFail";
        }
        else if(userVO.getU_ok() == "0" || userVO.getU_ok().equals("0"))
        {
            userVO = null;
            System.out.println("Please read your mail.");
            return "noMail";
        }
        else
        {
            session.setAttribute("U_NUM", userVO.getU_num());
            session.setAttribute("U_NICK", userVO.getU_nick());
            session.setAttribute("U_LEVEL", userVO.getU_level());

            System.out.println("Session U_NICK : [ " + (String) session.getAttribute("U_NICK") +" ]");
            System.out.println("Session U_NUM  : [ " + (int)    session.getAttribute("U_NUM")  +" ]");
            System.out.println("Session U_LEVEL: [ " + (int)    session.getAttribute("U_LEVEL")+" ]");
            System.out.println("Login OK, User cheak OK.");

            return "loginOk";
        }

    }

    @GetMapping("/novelnet/logout.do")
    public String logout(HttpSession session) throws Exception{
        session.removeAttribute("U_NUM");
        session.removeAttribute("U_NICK");
        session.removeAttribute("U_LEVEL");
        return "redirect:/novelnet";
    }

    @GetMapping("/novelnet/test.do")
    public String test(@RequestParam(value = "num",  required = false) String num,
                       @RequestParam(value = "count", required = false) String count, Model model) throws Exception{

        return "test";
    }
}
