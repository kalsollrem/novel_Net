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

        System.out.println("======================================");

        if(keyword == null)                   {keyword = "";    }

        if(page    == null)                   {page    = "1";   }
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

        System.out.println("검색 메인 태그"+mainTag);

        //검색갯수 확보(메인테그, 검색태그, 검색타입, 검색키워드)
        int count = searchMapper.searchNovelCount(mainTag,searchTag,searchType,keyword);
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

        System.out.println("정렬 : "+sort+"/ 메인테그 : "+mainTag+"/ 검색 태그 : "+searchTag+"/ 검색 타입:"+searchType+"/");


        //검색(검색조건, 메인태그, 검색태그, 검색카테고리, 검색어, 시작점 순서)
        List<NovelVO> novelList = searchMapper.getSearchNovelList(sort,mainTag,searchTag,searchType, keyword, start);
        //System.out.println(novelList);
        model.addAttribute("novelList",novelList);

        //년도확인
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        model.addAttribute("year",year);

        return "search";
    }


    @GetMapping("novelnet/searchPlus")
    public String searchPlusPage(HttpSession session,
                             @RequestParam(value = "sort"      ,required = false) String sort,
                             @RequestParam(value = "searchType",required = false) String searchType,
                             @RequestParam(value = "mainTag"   ,required = false) String mainTag,
                             @RequestParam(value = "searchTag" ,required = false) String searchTag,
                             @RequestParam(value = "novelType" ,required = false) String novelType,
                             @RequestParam(value = "monopoly"  ,required = false) String monopoly,
                             @RequestParam(value = "doType"    ,required = false) String doType,
                             @RequestParam(value = "dateType"  ,required = false) String dateType,
                             @RequestParam(value = "page"      ,required = false) String page,
                             Model model) throws Exception{
//        dateType : 신작(newNovel), 완결(finNovel)
//        novelType : 자유연재(free), 프라임(prime)
//        monopoly : 플랫폼독점(only), 자유(free)
//        doType : 연재중(newNovel), 완결(finNovel)

        System.out.println("======================================");


        if(dateType    == null)               {dateType  = "";}
        if(novelType    == null)              {novelType = "";}
        if(monopoly    == null)               {monopoly  = "";}
        if(doType    == null)                 {doType    = "";}

        if(page    == null)                   {page    = "1";   }
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

        System.out.println("검색 메인 태그"+mainTag);

        //검색갯수 확보(메인테그, 검색태그, 검색타입, 검색키워드)
        int count = searchMapper.searchNovelCount(mainTag,searchTag,searchType,"");
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

        model.addAttribute("searchCount", count);
        model.addAttribute("allPage", allPage);
        model.addAttribute("nowCase", nowCase);
        model.addAttribute("allCase", allCase);
        model.addAttribute("leftPage", leftPage);
        model.addAttribute("rightPage", rightPage);
        model.addAttribute("displayPage", displayPage);

        //시작페이지처리
        int start = (Integer.parseInt(page)-1)*10;

        System.out.println("정렬 : "+sort+"/ 메인테그 : "+mainTag+"/ 검색 태그 : "+searchTag+"/ 검색 타입:"+searchType+"/");


        //검색(검색조건, 메인태그, 검색태그, 검색카테고리, 검색어, 시작점 순서)
        List<NovelVO> novelList = searchMapper.getSearchPlusNovelList(sort,mainTag,searchTag,dateType,novelType,doType,monopoly,start);
        //System.out.println(novelList);
        model.addAttribute("novelList",novelList);

        //년도확인
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        model.addAttribute("year",year);

        return "search_plus";
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
