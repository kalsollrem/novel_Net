package com.project.novelnet.controller;
import com.project.novelnet.Vo.MasterVO.MasterMemoVO;
import com.project.novelnet.Vo.NovelVO;
import com.project.novelnet.Vo.ReplyVO;
import com.project.novelnet.Vo.TagVO;
import com.project.novelnet.Vo.UserVO;
import com.project.novelnet.repository.NovelRepository;
import com.project.novelnet.repository.ProfillMapper;
import com.project.novelnet.repository.UserMapper;
import com.project.novelnet.service.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private FileUploadService fileUploadService;

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
    public String JoinCheak(@Param("code") String code,Model model)throws Exception{
        if(code == null)
        {
            { model.addAttribute("message", "가입에 실패하였습니다. 코드를 다시 확인해주세요."); }
            return "message";
        }
        else {

        String ok = "none";
        try  {ok = userMapper.UesrJoinFind(code);}
        catch (Exception e) {}

        if (ok != "none")
        {
            int propit = userMapper.UesrJoinEnd(ok);
            if(propit > 0){ model.addAttribute("message", "인증에 성공하였습니다."); }
            else          { model.addAttribute("message", "인증에 실패하였습니다. 메일의 코드를 다시 확인해주세요."); }
        }
        else          { model.addAttribute("message", "인증에 실패하였습니다. 코드를 다시 확인해주세요."); }
        }

        return "message";
    }

    @GetMapping("/novelnet/login")
    public void getLoginData() throws Exception{}

    @PostMapping("/novelnet/login")
    public String getLoginData(UserVO userVO, HttpSession session) throws Exception{
        System.out.println(userVO.getU_mail()+" / "+userVO.getU_pass());

        return "redirect:/novelnet";
    }

    //내 프로필 바로가기
    @GetMapping("/novelnet/goMyProfill")
    public String goMyProfillPage(HttpSession session) throws Exception
    {
        String u_num;
        try {u_num= (String)session.getAttribute("U_NUM").toString();}  catch (Exception e) {return "redirect:/novelnet";}

        return "redirect:/novelnet/profill?user="+u_num+"&type=novels";
    }

    //프로필 페이지
    @GetMapping("/novelnet/profill")
    public String profillPage(Model model,
                              @Param("user") String user,
                              @Param("page") String page,
                              @Param("pageR") String pageR,
                              HttpSession session) throws Exception
    {
        String u_num;
        try {u_num= (String)session.getAttribute("U_NUM").toString();}  catch (Exception e) {u_num = null;}
//        String u_num="29";
        //페이징용 함수
        int count, allPage,nowCase,allCase,leftPage,rightPage,displayPage,start;

        //유저 존재여부 확인
        if (manageService.isInteger(user) == true && profillMapper.findProfillOK(user) == 1)
        {
            //선호태그
            TagVO tvo = profillMapper.likeTagAndRcnt(user);
            model.addAttribute("tvo",tvo);

            //소설페이지
            if(page == null || page == "0")                     {page    = "1"; }
            else{ if(manageService.isInteger(page) == false)    {page    = "1";}}

            //댓글페이지
            if(pageR == null || pageR == "0")                   {pageR   = "1"; }
            else{ if(manageService.isInteger(pageR) == false)   {pageR   = "1";}}

                    //소설페이징처리

                    count = profillMapper.getProfillNoveCnt(user);

                    pageingService.setNowPage(page);
                    pageingService.setTotalCount(count);

                    allPage     = pageingService.getAllPage();
                    nowCase     = pageingService.getNowCase();
                    allCase     = pageingService.getAllCase();
                    leftPage    = pageingService.getLeftPage();
                    rightPage   = pageingService.getRightPage();
                    displayPage = pageingService.getDisplayPage();

                    System.out.println("allPage:" + allPage);
                    System.out.println("nowCase:" + nowCase);
                    System.out.println("allCase:" + allCase);
                    System.out.println("leftPage:" + leftPage);
                    System.out.println("rightPage:" + rightPage);
                    System.out.println("displayPage:" + displayPage);

                    model.addAttribute("allPage", allPage);
                    model.addAttribute("nowCase", nowCase);
                    model.addAttribute("allCase", allCase);
                    model.addAttribute("leftPage", leftPage);
                    model.addAttribute("rightPage", rightPage);
                    model.addAttribute("displayPage", displayPage);

                    //시작페이지처리
                    start = (Integer.parseInt(page)-1)*10;

            //내 소설 리스트
            List<NovelVO> novelList = profillMapper.getProfillNovelList(user,start);
            model.addAttribute("novelList", novelList);

            //보여줄 유저 데이터 확보(게스트는 프로필+사진)
            String who;
            UserVO profillData;
            ArrayList<ReplyVO> replyVOS;

            //프로필 데이터 입력
            if(user.equals(u_num))  //자신
            {
                profillData = profillMapper.getProfill(u_num);
                who="me";

                count = profillMapper.getMyReplyCnt(user);

                pageingService.setNowPage(pageR);
                pageingService.setTotalCount(count);

                allPage     = pageingService.getAllPage();
                nowCase     = pageingService.getNowCase();
                allCase     = pageingService.getAllCase();
                leftPage    = pageingService.getLeftPage();
                rightPage   = pageingService.getRightPage();
                displayPage = pageingService.getDisplayPage();

                model.addAttribute("allPageR", allPage);
                model.addAttribute("nowCaseR", nowCase);
                model.addAttribute("allCaseR", allCase);
                model.addAttribute("leftPageR", leftPage);
                model.addAttribute("rightPageR", rightPage);
                model.addAttribute("displayPageR", displayPage);

                //시작페이지처리
                start = (Integer.parseInt(pageR)-1)*10;

                replyVOS = profillMapper.getMyAllReply(u_num,start);
                model.addAttribute("replyVOS", replyVOS);
            }
            else
            {
                profillData = profillMapper.getMyself(user); who="you";

                model.addAttribute("allPageR", 1);
                model.addAttribute("nowCaseR", 1);
                model.addAttribute("allCaseR", 1);
                model.addAttribute("leftPageR", 1);
                model.addAttribute("rightPageR", 1);
                model.addAttribute("displayPageR", 1);
            }//게스트


            model.addAttribute("profillData", profillData);
            model.addAttribute("who", who);
            model.addAttribute("user", user);
            System.out.println(who +":"+ profillData);

            return "mypage";
        }else
        {
            return "redirect:/novelnet";
        }
    }

    //내 프로필 이미지 보기
    @GetMapping("/novelnet/profill/myImg")
    public String myImg(HttpSession session,
                        Model model) throws Exception
    {
        String u_num;
        try {u_num= (String)session.getAttribute("U_NUM").toString();}  catch (Exception e) {u_num = null;}

        if (u_num != null){
            //커버탐색
            String cover;
            try {cover = userMapper.findUserCover(u_num);}
            catch (Exception e){cover = "";}

            System.out.println(cover);

            if(cover != ""){ model.addAttribute("cover",cover); }
            return "image_modalA";
        }else
        {
            return "image_warnning";
        }
    }

    //프로필 이미지 등록
    @PostMapping("/iconChange.do")
    @ResponseBody
    public String iconChange(HttpSession session,
                                @RequestParam(value = "imgfile"    ,required = false) MultipartFile imgfile,
                                @RequestParam(value = "oldCover"   ,required = false) String oldCover,
                                MasterMemoVO masterMemoVO,
                                HttpServletRequest request)throws Exception
    {
        int cnt=0;
        String answer="no";

        System.out.println(imgfile);
        String u_num;
        try {u_num= (String)session.getAttribute("U_NUM").toString();}  catch (Exception e) {u_num = null;}

        //커버탐색
        try {if (oldCover != null){cnt = 1;}}
        catch (Exception e) {cnt = 0;}

        //신규 이미지 저장
        if (!imgfile.isEmpty())
        {
            if (cnt == 1) {fileUploadService.deleteFile(oldCover);}

            //이미지 등록
            answer = fileUploadService.ProfillUpload(imgfile);
        }

        if (!answer.equals("no")){
            int goChange = userMapper.changeProfill(u_num,answer);
            if(goChange == 1) {answer = "yes";}
        }

        return answer;
    }

    //프로필 수정
    @PostMapping("/profillUpdate.do")
    @ResponseBody
    public int profillUpdate(HttpSession session,
                             @RequestParam(value = "nick"   ,required = false) String nick,
                             @RequestParam(value = "pass"   ,required = false) String pass,
                             @RequestParam(value = "intro"  ,required = false) String intro,
                             MasterMemoVO masterMemoVO,
                             HttpServletRequest request)throws Exception
    {
        int cnt=0;

        String u_num;
        try {u_num= (String)session.getAttribute("U_NUM").toString();}  catch (Exception e) {u_num = null;}

        //커버탐색
        try {if (intro == null){intro = "";}}
        catch (Exception e) {intro = "";}

        cnt = userMapper.updateUserDate(u_num,nick,pass,intro);


        return cnt;
    }
}
