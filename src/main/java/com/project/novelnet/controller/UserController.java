package com.project.novelnet.controller;
import com.project.novelnet.Vo.UserVO;
import com.project.novelnet.repository.NovelRepository;
import com.project.novelnet.service.MailService;
import com.project.novelnet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    private final NovelRepository novelRepository;

    public UserController(NovelRepository novelRepository) {
        this.novelRepository = novelRepository;
    }

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    //아이디 중복 검사
    @PostMapping("/idCheck")
    @ResponseBody
    public int idCheck(@RequestParam("id") String id) throws Exception {

        int cnt = userService.IdCheak(id);
        System.out.println("ID명 : " + id);
        return cnt;
    }

    //가입
    @PostMapping("/novelnet/join.do")
    public String joinForm(UserVO userVO, Model model)throws Exception{

        //등록
        novelRepository.userJoin(userVO);
        System.out.println("아이디"+userVO.getU_mail() + "/ 비번 : " + userVO.getU_pass()+ "/ 닉 : "+userVO.getU_nick() + "/ 유저 번호" + userVO.getU_num());

        //검증
        String code = novelRepository.findCode(userVO.getU_num());

        if(code == null)
        {
            System.out.println("실패하였습니다.");
            model.addAttribute("message", "가입에 실패하였습니다");
            model.addAttribute("searchUrl", "/novelnet");

        }
        else
        {
            mailService.mailSend(userVO.getU_mail(), code);
            System.out.println("메일이 발송되었습니다.");
            model.addAttribute("message", "가입에 성공하였습니다. 메일 인증시 정식가입됩니다.");
            model.addAttribute("searchUrl", "/novelnet");
        }
        return "redirect:/novelnet";
    }

    //유저인증 메일 서비스
    @GetMapping("/novelnet/JoinCheak")
    public String JoinCheak(@RequestParam String code,Model model)throws Exception{
        System.out.println("코드 : "+code);

        if(code != null)
        {
            novelRepository.UesrOK(code);
        }
        model.addAttribute("message", "가입에 성공하였습니다. 메일 인증시 정식가입됩니다.");
        model.addAttribute("searchUrl", "/novelnet");
        return "message";
    }

    @GetMapping("/novelnet/login")
    public void getLoginData() throws Exception{}

    @PostMapping("/novelnet/login")
    public String getLoginData(UserVO userVO, HttpSession session) throws Exception{
        System.out.println(userVO.getU_mail()+" / "+userVO.getU_pass());
//        userVO = loginService.getLogin(userVO);
//        if (userVO != null){
//            session.setAttribute("userData", userVO);
//        }
        return "redirect:/novelnet";
    }


}
