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

        List<NovelVO> novelVOList = searchMapper.getSearchNovelList("n_date","","","title", "", 0);
        return "search";
    }
}
