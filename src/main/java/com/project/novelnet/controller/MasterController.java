package com.project.novelnet.controller;
import com.project.novelnet.Vo.NewPageingVO;
import com.project.novelnet.repository.MasterMapper;
import com.project.novelnet.service.ManageService;
import com.project.novelnet.service.PageingService;
import org.apache.ibatis.annotations.Param;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Controller
public class MasterController {
    @Autowired
    private MasterMapper masterMapper;

    @Autowired
    private ManageService manageService;

    @Autowired
    private PageingService pageingService;

    String u_num;



    //메인페이지
    @GetMapping("/master/index")
    public String masterIndex(Model model,
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
        try                 {if((String)session.getAttribute("U_LEVEL").toString() != "3"){System.out.println("나가");}}
        catch (Exception e) {System.out.println("나가");}

        //변수 처리
        if(page == null || page == "0")                     {page    = "1"; }
        else{ if(manageService.isInteger(page) == false)    {page    = "1";}}

        if(searchType == null || searchType.replace(" ","") == "")  {searchType = "";}
        if(keyword    == null || keyword.replace(" ","")    == "")  {keyword    = "";}



        //페이징처리
        int allPageCnt = masterMapper.novelShingoCnt(searchType,keyword);
        pageingService.setNowPage(page);
        pageingService.setTotalCount(allPageCnt);
        newPageingVO = pageingService.setNewPageingVO(newPageingVO);

        model.addAttribute("paging", newPageingVO);


        //시작페이지처리
        int start = (Integer.parseInt(page)-1)*10;
        List<Map<String, Object>> list = masterMapper.novelShingo(searchType,keyword,start);
        System.out.println("총"+allPageCnt+"개/");
        System.out.println(list);


        model.addAttribute("list",list);
        return "master_Page";
    }


    //유저 강등
    @PostMapping("/userStop.do")
    @ResponseBody
    public int memoDelete (@RequestParam("u_num") String  u_num,
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
}
