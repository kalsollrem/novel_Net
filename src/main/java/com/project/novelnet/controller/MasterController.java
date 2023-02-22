package com.project.novelnet.controller;
import com.project.novelnet.Vo.MasterVO.MasterNovel;
import com.project.novelnet.Vo.MasterVO.MasterReply;
import com.project.novelnet.Vo.NewPageingVO;
import com.project.novelnet.Vo.NovelVO;
import com.project.novelnet.Vo.PdPickVO;
import com.project.novelnet.Vo.UserVO;
import com.project.novelnet.repository.MasterMapper;
import com.project.novelnet.service.ManageService;
import com.project.novelnet.service.PageingService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


@Controller
public class MasterController {
    @Autowired
    private MasterMapper masterMapper;

    @Autowired
    private ManageService manageService;

    @Autowired
    private PageingService pageingService;

    String u_num;



    //신고소설 관리
    @GetMapping("/master/novelDeclaration")
    public String masterNovelDeclaration(Model model,
                                       HttpSession session,
                                       NewPageingVO newPageingVO,
                                       @Param("page")       String page,
                                       @Param("searchType") String searchType,
                                       @Param("keyword")    String keyword)throws Exception
    {
        //아이디 처리
        try                 {u_num= (String)session.getAttribute("U_NUM").toString();}
        catch (Exception e) {u_num = null;}

        //레벨확인
        try                 {if((String)session.getAttribute("U_LEVEL").toString() != "9"){System.out.println("관리자(9레벨)가아님");}}
        catch (Exception e) {System.out.println("나가");}

        //변수 처리
        if(page == null || page == "0")                     {page    = "1"; }
        else{ if(manageService.isInteger(page) == false)    {page    = "1";}}

        if(searchType == null || searchType.replace(" ","") == "")  {searchType = "";}
        model.addAttribute("searchType",searchType);

        if(keyword    == null || keyword.replace(" ","")    == "")  {keyword    = "";}


        //페이징처리
        int allPageCnt = masterMapper.novelShingoCnt(searchType,keyword);
        pageingService.setNowPage(page);
        pageingService.setTotalCount(allPageCnt);
        newPageingVO = pageingService.setNewPageingVO(newPageingVO);

        model.addAttribute("paging", newPageingVO);


        //시작페이지처리
        int start = (Integer.parseInt(page)-1)*10;
        List<MasterNovel> list = masterMapper.novelShingo(searchType,keyword,start);
        System.out.println("총"+allPageCnt+"개/");
        System.out.println(list);


        model.addAttribute("list",list);
        return "master_novelDeclaration";
    }


    //유저 강등
    @PostMapping("/userManage.do")
    @ResponseBody
    public int userStop   (@RequestParam("u_num") String  u_num,
                           @RequestParam("switchUD") int switchUD,
                           HttpSession session)throws Exception
    {
        //1:성공, 0:실패
        int answer = 0;

//        if((String)session.getAttribute("U_LEVEL").toString() == "9"){
            answer = masterMapper.UserLevelChanger(switchUD,u_num);
//        }

        //작성자 권한 확인
        return answer;
    }



    //경고삭제
    @PostMapping("/warnningDel.do")
    @ResponseBody
    public int warnningDel (@RequestParam("mw_num") int mw_num,
                            HttpSession session)throws Exception
    {
        //1:성공, 0:실패
        int answer = 0;

//        if((String)session.getAttribute("U_LEVEL").toString() == "9"){
            answer = masterMapper.warningDel(mw_num);
//        }

        //작성자 권한 확인
        return answer;
    }


    //신고리플 관리
    @GetMapping("/master/replyDeclaration")
    public String masterReplyDeclaration(Model model,
                                          HttpSession session,
                                          NewPageingVO newPageingVO,
                                          @Param("page")       String page,
                                          @Param("searchType") String searchType,
                                          @Param("keyword")    String keyword)throws Exception
    {
        //아이디 처리
        try                 {u_num= (String)session.getAttribute("U_NUM").toString();}
        catch (Exception e) {u_num = null;}

        //레벨확인
        try                 {if((String)session.getAttribute("U_LEVEL").toString() != "9") {System.out.println("관리자(9레벨)가아님");}}
        catch (Exception e) {System.out.println("나가");}

        //변수 처리
        if(page == null || page == "0")                     {page    = "1"; }
        else{ if(manageService.isInteger(page) == false)    {page    = "1";}}

        if(searchType == null || searchType.replace(" ","") == "")  {searchType = "";}
        model.addAttribute("searchType",searchType);

        if(keyword    == null || keyword.replace(" ","")    == "")  {keyword    = "";}


        System.out.println(keyword);
        //페이징처리
        int allPageCnt = masterMapper.replyShingoCnt(searchType,keyword);
        pageingService.setNowPage(page);
        pageingService.setTotalCount(allPageCnt);
        newPageingVO = pageingService.setNewPageingVO(newPageingVO);

        System.out.println("allPage:" + newPageingVO.getAllPage());
        System.out.println("nowCase:" + newPageingVO.getNowCase());
        System.out.println("allCase:" + newPageingVO.getAllCase());

        model.addAttribute("paging", newPageingVO);


        //시작페이지처리
        int start = (Integer.parseInt(page)-1)*10;
        List<MasterReply> list = masterMapper.replyShingo(searchType,keyword,start);
        System.out.println("총"+allPageCnt+"개/");
        System.out.println(list);


        model.addAttribute("list",list);



        return "master_replyDeclaration";
    }

    //댓글 블라인드
    @PostMapping("/blindDel.do")
    @ResponseBody
    public int blindDel (@RequestParam("r_num") String r_num,
                         HttpSession session)throws Exception
    {
        //1:성공, 0:실패
        int answer = 0;

//        if((String)session.getAttribute("U_LEVEL").toString() == "9"){
        answer = masterMapper.replyBlind(r_num);
//        }

        //작성자 권한 확인
        return answer;
    }


    //소설관리
    @GetMapping("/master/novelManagement")
    public String masterNovelManage(Model model,
                                         HttpSession session,
                                         NewPageingVO newPageingVO,
                                         @Param("page")       String page,
                                         @Param("searchType") String searchType,
                                         @Param("keyword")    String keyword,
                                         @Param("sort")       String sort)throws Exception
    {
        //아이디 처리
        try                 {u_num= (String)session.getAttribute("U_NUM").toString();}
        catch (Exception e) {u_num = null;}

        //레벨확인
        try                 {if((String)session.getAttribute("U_LEVEL").toString() != "9") {System.out.println("관리자(9레벨)가아님");}}
        catch (Exception e) {System.out.println("나가");}

        //변수 처리
        if(page == null || page == "0")                     {page    = "1"; }
        else{ if(manageService.isInteger(page) == false)    {page    = "1";}}

        if(searchType == null || searchType.replace(" ","") == "")  {searchType = "";}
        model.addAttribute("searchType",searchType);

        if(keyword    == null || keyword.replace(" ","")    == "")  {keyword    = "";}

        if(sort == null || sort == "")                      {sort    = "all"; }


        //페이징처리
        int allPageCnt = masterMapper.novelCnt(searchType,keyword, sort);
        System.out.println(allPageCnt);
        pageingService.setNowPage(page);
        pageingService.setTotalCount(allPageCnt);
        newPageingVO = pageingService.setNewPageingVO(newPageingVO);

        System.out.println("allPage:" + newPageingVO.getAllPage());
        System.out.println("nowCase:" + newPageingVO.getNowCase());
        System.out.println("allCase:" + newPageingVO.getAllCase());

        model.addAttribute("paging", newPageingVO);


        //시작페이지처리
        int start = (Integer.parseInt(page)-1)*10;
        List<NovelVO> list = masterMapper.masterNovelList(searchType,keyword, sort, start);
        System.out.println("총"+allPageCnt+"개/");



        model.addAttribute("list",list);

        //pd픽 관리
        List<PdPickVO> pdPick = masterMapper.pdPickList();
        model.addAttribute("pdPick",pdPick);


        return "master_novelManage";
    }

    //소설 정지 앤 해제
    @PostMapping("/masterNovelSwitch.do")
    @ResponseBody
    public int masterNovelSwitch   (@RequestParam("n_num") int  n_num,
                                    @RequestParam("switchUD") int switchUD,
                                    HttpSession session)throws Exception
    {
        //1:성공, 0:실패
        int answer = 0;
        System.out.println(n_num+'/'+ switchUD);
//        if((String)session.getAttribute("U_LEVEL").toString() == "9"){
        answer = masterMapper.masterNovelSwitch(n_num,switchUD);
//        }

        //작성자 권한 확인
        return answer;
    }

    //소설 정지 앤 해제
    @PostMapping("/pdPickChoice.do")
    @ResponseBody
    public int masterPdPickChoice   (@RequestParam("n_num") int  n_num,
                                    @RequestParam("switchUD") int switchUD,
                                    HttpSession session)throws Exception
    {
        //1:성공, 0:실패
        int answer = 0;

//        if((String)session.getAttribute("U_LEVEL").toString() == "9"){
            if(switchUD == 0){answer = masterMapper.pdPickChoice(n_num);}
            else             {answer = masterMapper.pdPickDelete(n_num);}
//        }

        //작성자 권한 확인
        return answer;
    }


    //유저관리
    @GetMapping("/master/userManagement")
    public String userManagement(Model model,
                                    HttpSession session,
                                    NewPageingVO newPageingVO,
                                    @Param("page")       String page,
                                    @Param("searchType") String searchType,
                                    @Param("keyword")    String keyword,
                                    @Param("sort")       String sort)throws Exception
    {
        //아이디 처리
        try                 {u_num= (String)session.getAttribute("U_NUM").toString();}
        catch (Exception e) {u_num = null;}

        //레벨확인
        try                 {if((String)session.getAttribute("U_LEVEL").toString() != "9") {System.out.println("관리자(9레벨)가아님");}}
        catch (Exception e) {System.out.println("나가");}

        //변수 처리
        if(page == null || page == "0")                     {page    = "1"; }
        else{ if(manageService.isInteger(page) == false)    {page    = "1";}}

        if(searchType == null || searchType.replace(" ","") == "")  {searchType = "";}
        model.addAttribute("searchType",searchType);

        if(keyword    == null || keyword.replace(" ","")    == "")  {keyword    = "";}

        if(sort == null || sort == "")                      {sort    = "all"; }


        //페이징처리
        int allPageCnt = masterMapper.userCnt();
        pageingService.setNowPage(page);
        pageingService.setTotalCount(allPageCnt);
        newPageingVO = pageingService.setNewPageingVO(newPageingVO);

        model.addAttribute("paging", newPageingVO);


        //시작페이지처리
        int start = (Integer.parseInt(page)-1)*10;
        List<UserVO> list = masterMapper.masterUserList("","","",start);
        System.out.println("총"+allPageCnt+"개/");
        System.out.println(list);


        model.addAttribute("list",list);


        return "master_userManagement";
    }
}
