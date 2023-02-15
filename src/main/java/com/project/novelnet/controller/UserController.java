package com.project.novelnet.controller;
import com.project.novelnet.Vo.NovelVO;
import com.project.novelnet.Vo.ReplyVO;
import com.project.novelnet.Vo.TagVO;
import com.project.novelnet.Vo.UserVO;
import com.project.novelnet.repository.NovelRepository;
import com.project.novelnet.repository.ProfillMapper;
import com.project.novelnet.repository.UserMapper;
import com.project.novelnet.service.MailService;
import com.project.novelnet.service.ManageService;
import com.project.novelnet.service.PageingService;
import com.project.novelnet.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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

    @Autowired
    private ManageService manageService;

    @Autowired
    private PageingService pageingService;

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
                              @Param("user") String user,
                              @Param("page") String page,
                              HttpSession session) throws Exception
    {
        String u_num;
        try {u_num= (String)session.getAttribute("U_NUM").toString();}  catch (Exception e) {u_num = null;}

        //유저 존재여부 확인
        if (manageService.isInteger(user) == true && profillMapper.findProfillOK(user) == 1)
        {
            //선호태그
            TagVO tvo = profillMapper.likeTagAndRcnt(user);
            model.addAttribute("tvo",tvo);

            //내 소설 리스트
            List<NovelVO> novelList = profillMapper.getProfillNovelList(user);
            model.addAttribute("novelList", novelList);

            //보여줄 유저 데이터 확보(게스트는 프로필+사진)
            String who;
            UserVO profillData;
            ArrayList<ReplyVO> replyVOS;


            //페이징 처리
            if(page    == null)                     {page    = "1";   }
            else{
                if(manageService.isInteger(page) == false){page = "1"; }
            }
            int count = profillMapper.getProfillNoveCnt(user);

            pageingService.setNowPage(page);
            pageingService.setTotalCount(count);

            int allPage     = pageingService.getAllPage();
            int nowCase     = pageingService.getNowCase();
            int allCase     = pageingService.getAllCase();
            int leftPage    = pageingService.getLeftPage();
            int rightPage   = pageingService.getRightPage();
            int displayPage = pageingService.getDisplayPage();

            System.out.println("전채 간격 " + allCase);
            System.out.println("현재 간격 " + nowCase);
            System.out.println("전 버튼 " + leftPage);
            System.out.println("후 버튼 " + rightPage);
            System.out.println("하단에 나온 페이지 " + displayPage);

            model.addAttribute("allPage", allPage);
            model.addAttribute("nowCase", nowCase);
            model.addAttribute("allCase", allCase);
            model.addAttribute("leftPage", leftPage);
            model.addAttribute("rightPage", rightPage);
            model.addAttribute("displayPage", displayPage);

            //시작페이지처리
            int start = (Integer.parseInt(page)-1)*10;


            //프로필 데이터 입력
            if(user.equals(u_num)){ profillData = profillMapper.getProfill(u_num); who="me"; replyVOS = profillMapper.getMyAllReply(u_num); model.addAttribute("replyVOS", replyVOS);} //자신
            else                  { profillData = profillMapper.getMyself(user);   who="you";} //게스트

            model.addAttribute("profillData", profillData);
            model.addAttribute("who", who);
            System.out.println(who +":"+ profillData);

            return "mypage";
        }else
        {
            return "redirect:/novelnet";
        }
    }

}
