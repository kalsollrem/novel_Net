package com.project.novelnet.controller;

import com.project.novelnet.Vo.NovelVO;
import com.project.novelnet.repository.SearchMapper;
import com.project.novelnet.service.ManageService;
import com.project.novelnet.service.PageingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.List;

@Controller
public class SearchController
{
    @Autowired
    private SearchMapper searchMapper;

    //페이징
    @Autowired
    private PageingService pageingService;

    //편의용
    @Autowired
    private ManageService manageService;

    @GetMapping("novelnet/search")
    public String searchPage(HttpSession session,
                             @RequestParam(value = "keyword"   ,required = false) String keyword,
                             @RequestParam(value = "newOld"    ,required = false) String newOld,
                             @RequestParam(value = "category" ,required = false) String category,
                             @RequestParam(value = "page"      ,required = false) String page,
                             Model model) throws Exception{

        String u_num;
        System.out.println("======================================");

        if(session.getAttribute("U_NUM") != null) {u_num = (String) session.getAttribute("U_NUM").toString(); } //유저번호
        else                                            {u_num = "20"; } //비로그인 테스트용

        if(keyword == null)                     {keyword = "";    }

        if(newOld==null)                        {newOld  = "desc";}
        else if (newOld.equals("asc"))          {newOld  = "asc"; }
        else                                    {newOld  = "desc";}

        if (category == null)                  {category = "";  }
        else {
            switch (category){
                case "doWrite"  : category = "doing" ; break;
                case "compWrite": category = "done"  ; break;
                default         : category = ""      ; break;
            }
        }
        if(page    == null)                     {page    = "1";   }
        else{
            if(manageService.isInteger(page) == false){page = "1"; }
        }
        System.out.println("인저가항2:"+keyword +"/"+ newOld +"/"+category+"/"+page);

        //검색갯수 검색
        if (u_num != null){

            //검색갯수 확보
            int count =1; //sql추가
            System.out.println("갯수 : " + count);

            //페이징 처리
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

            //검색(검색조건, 메인태그, 검색태그, 검색카테고리, 검색어, 시작점 순서)
            List<NovelVO> novelList = searchMapper.getSearchNovelList("n_date","","","title", "", 0);
            System.out.println(novelList);
            model.addAttribute("novelList",novelList);

            //년도확인
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            model.addAttribute("year",year);

        }


        List<NovelVO> novelVOList = searchMapper.getSearchNovelList("n_date","","","title", "", 0);
        return "search";
    }
}
