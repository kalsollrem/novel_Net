package com.project.novelnet.controller;
import com.project.novelnet.Vo.NovelVO;
import com.project.novelnet.Vo.TagVO;
import com.project.novelnet.Vo.UserVO;
import com.project.novelnet.repository.NovelRepository;
import com.project.novelnet.repository.ProfillMapper;
import com.project.novelnet.repository.UserMapper;
import com.project.novelnet.service.MailService;
import com.project.novelnet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

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

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProfillMapper profillMapper;

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
    @PostMapping("/passfinder.do")
    @ResponseBody
    public String passfinder(@RequestParam String findid, HttpServletRequest request, HttpSession session)throws Exception{

        String answer;

        System.out.println(findid);

        String password = userMapper.getLostId(findid);
        System.out.println(password);

        //프론트에 보낼 답변
        if (password == null || password == "")
        {
            answer = "fail";
        }else
        {
            answer = "findOK";
            mailService.passFinemailSend(findid, password);
        }
        System.out.println(answer);



        return answer;
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

        return "redirect:/novelnet";
    }


    @GetMapping("/novelnet/profill")
    public String profillPage(Model model,
                              String user,
                              HttpSession session) throws Exception
    {
        String u_num = "20";

        if (user == null){
            return "redirect:/novelnet";
        }else
        {
            //선호태그
            List<TagVO> tvo = profillMapper.likeTagAndRcnt(u_num);
            model.addAttribute("tvo",tvo);

            //내 소설 리스트
            List<NovelVO> novelVO = profillMapper.getProfillNovelList(u_num);
            model.addAttribute("novelVO", novelVO);

            if(user == u_num)
            {
                //유저 정보 추가.
            }

            return "mypage";
        }
    }

}
