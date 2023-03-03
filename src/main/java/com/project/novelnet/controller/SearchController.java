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
                             @RequestParam(value = "sort"      ,required = false) String sort,
                             @RequestParam(value = "searchType",required = false) String searchType,
                             @RequestParam(value = "mainTag"   ,required = false) String mainTag,
                             @RequestParam(value = "searchTag" ,required = false) String searchTag,
                             @RequestParam(value = "keyword"   ,required = false) String keyword,
                             @RequestParam(value = "page"      ,required = false) String page,
                             Model model) throws Exception{


        if(keyword == null)                   {keyword = "";    }

        if(page    == null ||page=="0" ||page=="")      {page    = "1";   }
        else{ if(manageService.isInteger(page) == false){page = "1"; } }

        if(sort==null)                        {sort  = "n_date";}  //날짜
        else {
            switch (sort){
                case "view" : sort  = "n_count"     ; break;    //조회수
                case "vote" : sort  = "n_good"      ; break;    //추천수
                default     : sort  = "n_date"      ; break;    //날짜
            }
        }

        if(searchType==null)                        {searchType  = "title";}
        else {
            switch (searchType){
                case "writer":       searchType  = "writer"            ; break;    //닉네임
                case "introduction": searchType  = "introduction"    ; break;    //소개문
                default            : searchType  = "title"           ; break;    //제목
                }
        }


        if (mainTag == null)                  {mainTag = "";  }
        else {
            switch (mainTag){
                case "t_02": mainTag = "판타지"; break;
                case "t_03": mainTag = "무협"  ; break;
                case "t_04": mainTag = "현대"  ; break;
                case "t_05": mainTag = "로맨스"; break;
                case "t_06": mainTag = "대체역사"  ; break;
                case "t_07": mainTag = "공포"  ; break;
                case "t_08": mainTag = "SF"   ; break;
                case "t_09": mainTag = "스포츠"; break;
                case "t_10": mainTag = "기타"  ; break;
                default    : mainTag = ""     ; break;
            }
        }

        if(searchTag==null)                   {searchTag  = "";}  //검색태그가 비어있을 경우

        //검색갯수 확보(메인테그, 검색태그, 검색타입, 검색키워드)
        int count = searchMapper.searchNovelCount(mainTag,searchTag,searchType,keyword);

        //페이징 처리
        pageingService.setNowPage(page);
        pageingService.setTotalCount(count);

        int allPage     = pageingService.getAllPage();
        int nowCase     = pageingService.getNowCase();
        int allCase     = pageingService.getAllCase();
        int leftPage    = pageingService.getLeftPage();
        int rightPage   = pageingService.getRightPage();
        int displayPage = pageingService.getDisplayPage();


        model.addAttribute("keyword", keyword);
        model.addAttribute("searchCount", count);
        model.addAttribute("allPage", allPage);
        model.addAttribute("nowCase", nowCase);
        model.addAttribute("allCase", allCase);
        model.addAttribute("leftPage", leftPage);
        model.addAttribute("rightPage", rightPage);
        model.addAttribute("displayPage", displayPage);

        //시작페이지처리
        int start = (Integer.parseInt(page)-1)*10;



        //검색(검색조건, 메인태그, 검색태그, 검색카테고리, 검색어, 시작점 순서)
        List<NovelVO> novelList = searchMapper.getSearchNovelList(sort,mainTag,searchTag,searchType, keyword, start);
        model.addAttribute("novelList",novelList);

        //년도확인
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        model.addAttribute("year",year);

        return "search";
    }


    //자유연재 페이지
    @GetMapping("novelnet/searchPlus")
    public String searchPlusPage(HttpSession session,
                             @RequestParam(value = "sort"      ,required = false) String sort,
                             @RequestParam(value = "mainTag"   ,required = false) String mainTag,
                             @RequestParam(value = "searchTag" ,required = false) String searchTag,
                             @RequestParam(value = "monopoly"  ,required = false) String monopoly,
                             @RequestParam(value = "doType"    ,required = false) String doType,
                             @RequestParam(value = "page"      ,required = false) String page,
                             Model model) throws Exception{


        //연재방식
        String novelType = "free";


        //monopoly : 플랫폼독점(only), 자유(free)
        if(monopoly == null || monopoly=="")  {monopoly  = "free";}
        else if(monopoly  == "free")          {monopoly  = "free";}
        else                                  {monopoly  = "only";}


        //doType : 연재중(doNovel), 신작만(newNovel), 완결(finNovel)
        if(doType    == null)               {doType    = "";}
        else {
            switch (doType){
                case "doNovel"  : doType  = "doNovel"      ; break;    //연재중
                case "newNovel" : doType  = "newNovel"     ; break;    //신작만
                case "finNovel" : doType  = "finNovel"     ; break;    //완결만
                default         : doType  = ""             ; break;    //전체
            }
        }

        //페이지
        if(page    == null)                             {page = "1";   }
        else{ if(manageService.isInteger(page) == false){page = "1"; } }


        //정렬방식
        if(sort==null)                                  {sort  = "n_date";}  //날짜
        else {
            switch (sort){
                case "view" : sort  = "n_count"     ; break;    //조회수
                case "vote" : sort  = "n_good"      ; break;    //추천수
                default     : sort  = "n_date"      ; break;    //날짜
            }
        }


        //메인태그
        if (mainTag == null)                  {mainTag = "";  }
        else {
            switch (mainTag){
                case "t_02": mainTag = "판타지"; break;
                case "t_03": mainTag = "무협"  ; break;
                case "t_04": mainTag = "현대"  ; break;
                case "t_05": mainTag = "로맨스"; break;
                case "t_06": mainTag = "대체역사"  ; break;
                case "t_07": mainTag = "공포"  ; break;
                case "t_08": mainTag = "SF"   ; break;
                case "t_09": mainTag = "스포츠"; break;
                case "t_10": mainTag = "기타"  ; break;
                default    : mainTag = ""     ; break;
            }
        }

        //서브 검색태그
        if(searchTag==null)                   {searchTag  = "";}  //검색태그가 비어있을 경우

        //검색갯수 확보(메인테그, 검색태그, 검색타입, 검색키워드)
        int count = searchMapper.searchPlusNovelCount(mainTag,searchTag,novelType,doType,monopoly);

        //페이징 처리
        pageingService.setNowPage(page);
        pageingService.setTotalCount(count);

        int allPage     = pageingService.getAllPage();
        int nowCase     = pageingService.getNowCase();
        int allCase     = pageingService.getAllCase();
        int leftPage    = pageingService.getLeftPage();
        int rightPage   = pageingService.getRightPage();
        int displayPage = pageingService.getDisplayPage();


        model.addAttribute("searchCount", count);
        model.addAttribute("allPage", allPage);
        model.addAttribute("nowCase", nowCase);
        model.addAttribute("allCase", allCase);
        model.addAttribute("leftPage", leftPage);
        model.addAttribute("rightPage", rightPage);
        model.addAttribute("displayPage", displayPage);

        //시작페이지처리
        int start = (Integer.parseInt(page)-1)*10;

        //검색(검색조건, 메인태그, 검색태그, 검색카테고리, 검색어, 시작점 순서)
        List<NovelVO> novelList = searchMapper.getSearchPlusNovelList(sort,mainTag,searchTag,novelType,doType,monopoly,start);
        model.addAttribute("novelList",novelList);

        //년도확인
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        model.addAttribute("year",year);

        //신규 작품 등록버튼 작동확인
        if (session.getAttribute("U_NUM")!=null){
            model.addAttribute("writeGo", "writeGo");
        }

        return "search_plus";
    }
    //자유연재 페이지
    @GetMapping("novelnet/searchPrime")
    public String searchPrimePage(HttpSession session,
                             @RequestParam(value = "sort"      ,required = false) String sort,
                             @RequestParam(value = "mainTag"   ,required = false) String mainTag,
                             @RequestParam(value = "searchTag" ,required = false) String searchTag,
                             @RequestParam(value = "monopoly"  ,required = false) String monopoly,
                             @RequestParam(value = "doType"    ,required = false) String doType,
                             @RequestParam(value = "page"      ,required = false) String page,
                             Model model) throws Exception{

        //연재방식
        String novelType = "prime";


        //monopoly : 플랫폼독점(only), 자유(free)
        if(monopoly == null)                  {monopoly  = "";}
        else if(monopoly.equals("free"))      {monopoly  = "free";}
        else if(monopoly.equals("only"))      {monopoly  = "only";}
        else                                  {monopoly  = "";}


        //doType : 연재중(doNovel), 신작만(newNovel), 완결(finNovel)
        if(doType    == null)               {doType    = "";}
        else {
            switch (doType){
                case "doNovel"  : doType  = "doNovel"      ; break;    //연재중
                case "newNovel" : doType  = "newNovel"     ; break;    //신작만
                case "finNovel" : doType  = "finNovel"     ; break;    //완결만
                default         : doType  = ""             ; break;    //전체
            }
        }

        //페이지
        if(page    == null || page=="0" ||page=="")     {page = "1";   }
        else{ if(manageService.isInteger(page) == false){page = "1"; } }


        //정렬방식
        if(sort==null)                                  {sort  = "n_date";}  //날짜
        else {
            switch (sort){
                case "view" : sort  = "n_count"     ; break;    //조회수
                case "vote" : sort  = "n_good"      ; break;    //추천수
                default     : sort  = "n_date"      ; break;    //날짜
            }
        }


        //메인태그
        if (mainTag == null)                  {mainTag = "";  }
        else {
            switch (mainTag){
                case "t_02": mainTag = "판타지"; break;
                case "t_03": mainTag = "무협"  ; break;
                case "t_04": mainTag = "현대"  ; break;
                case "t_05": mainTag = "로맨스"; break;
                case "t_06": mainTag = "대체역사"  ; break;
                case "t_07": mainTag = "공포"  ; break;
                case "t_08": mainTag = "SF"   ; break;
                case "t_09": mainTag = "스포츠"; break;
                case "t_10": mainTag = "기타"  ; break;
                default    : mainTag = ""     ; break;
            }
        }

        //서브 검색태그
        if(searchTag==null)                   {searchTag  = "";}  //검색태그가 비어있을 경우

        //검색갯수 확보(메인테그, 검색태그, 검색타입, 검색키워드)
        int count = searchMapper.searchPlusNovelCount(mainTag,searchTag,novelType,doType,monopoly);

        //페이징 처리
        pageingService.setNowPage(page);
        pageingService.setTotalCount(count);

        int allPage     = pageingService.getAllPage();
        int nowCase     = pageingService.getNowCase();
        int allCase     = pageingService.getAllCase();
        int leftPage    = pageingService.getLeftPage();
        int rightPage   = pageingService.getRightPage();
        int displayPage = pageingService.getDisplayPage();


        model.addAttribute("searchCount", count);
        model.addAttribute("allPage", allPage);
        model.addAttribute("nowCase", nowCase);
        model.addAttribute("allCase", allCase);
        model.addAttribute("leftPage", leftPage);
        model.addAttribute("rightPage", rightPage);
        model.addAttribute("displayPage", displayPage);

        //시작페이지처리
        int start = (Integer.parseInt(page)-1)*10;

        //검색(검색조건, 메인태그, 검색태그, 검색카테고리, 검색어, 시작점 순서)
        List<NovelVO> novelList = searchMapper.getSearchPlusNovelList(sort,mainTag,searchTag,novelType,doType,monopoly,start);
        model.addAttribute("novelList",novelList);

        //년도확인
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        model.addAttribute("year",year);

        //신규 작품 등록버튼 작동확인
        if (session.getAttribute("U_NUM")!=null){
            model.addAttribute("writeGo", "writeGo");
        }

        return "search_prime";
    }

    @GetMapping("novelnet/ranking")
    public String bestranking(HttpSession session,
                             @RequestParam(value = "sort"      ,required = false) String sort,
                             @RequestParam(value = "searchType",required = false) String searchType,
                             @RequestParam(value = "mainTag"   ,required = false) String mainTag,
                             @RequestParam(value = "searchTag" ,required = false) String searchTag,
                             @RequestParam(value = "keyword"   ,required = false) String keyword,
                             @RequestParam(value = "page"      ,required = false) String page,
                             Model model) throws Exception{

        return "best";
    }
}
