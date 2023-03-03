package com.project.novelnet.controller;
import com.project.novelnet.Vo.MasterVO.MasterBannerVO;
import com.project.novelnet.Vo.MasterVO.MasterMemoVO;
import com.project.novelnet.Vo.MasterVO.MasterNovel;
import com.project.novelnet.Vo.MasterVO.MasterReply;
import com.project.novelnet.Vo.NewPageingVO;
import com.project.novelnet.Vo.NovelVO;
import com.project.novelnet.Vo.PdPickVO;
import com.project.novelnet.Vo.UserVO;
import com.project.novelnet.repository.MasterMapper;
import com.project.novelnet.service.FileUploadService;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class MasterController {
    @Autowired
    private MasterMapper masterMapper;

    @Autowired
    private ManageService manageService;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private PageingService pageingService;

    String u_num;
    String u_level;
    int level;


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
        try                 {level= (Integer)session.getAttribute("U_LEVEL");}
        catch (Exception e) {level = 0;}
        System.out.println("유저레벨:"+u_level);
        if(level != 9 || level == 0){return "redirect:/novelnet";}

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


    //회원삭제
    @PostMapping("/userOut.do")
    @ResponseBody
    public int userOut(@RequestParam("u_num") int u_num,
                       HttpSession session) throws Exception {
        //1:성공, 0:실패
        int answer = 0;

//        if((String)session.getAttribute("U_LEVEL").toString() == "9"){
            answer = masterMapper.deleteUser(u_num);
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
        try                 {level= (Integer)session.getAttribute("U_LEVEL");}
        catch (Exception e) {level = 0;}
        if(level != 9 || level == 0){return "redirect:/novelnet";}

        //변수 처리
        if(page == null || page == "0")                     {page    = "1"; }
        else{ if(manageService.isInteger(page) == false)    {page    = "1";}}

        if(searchType == null || searchType.replace(" ","") == "")  {searchType = "";}
        model.addAttribute("searchType",searchType);

        if(keyword    == null || keyword.replace(" ","")    == "")  {keyword    = "";}


        //페이징처리
        int allPageCnt = masterMapper.replyShingoCnt(searchType,keyword);
        pageingService.setNowPage(page);
        pageingService.setTotalCount(allPageCnt);
        newPageingVO = pageingService.setNewPageingVO(newPageingVO);

        model.addAttribute("paging", newPageingVO);


        //시작페이지처리
        int start = (Integer.parseInt(page)-1)*10;
        List<MasterReply> list = masterMapper.replyShingo(searchType,keyword,start);



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
        try                 {level= (Integer)session.getAttribute("U_LEVEL");}
        catch (Exception e) {level = 0;}

        if(level != 9 || level == 0){return "redirect:/novelnet";}

        //변수 처리
        if(page == null || page == "0")                     {page    = "1"; }
        else{ if(manageService.isInteger(page) == false)    {page    = "1";}}

        if(searchType == null || searchType.replace(" ","") == "")  {searchType = "";}
        model.addAttribute("searchType",searchType);

        if(keyword    == null || keyword.replace(" ","")    == "")  {keyword    = "";}

        if(sort == null || sort == "")                      {sort    = "all"; }
        model.addAttribute("sort",sort);


        //페이징처리
        int allPageCnt = masterMapper.novelCnt(searchType,keyword, sort);
        pageingService.setNowPage(page);
        pageingService.setTotalCount(allPageCnt);
        newPageingVO = pageingService.setNewPageingVO(newPageingVO);


        model.addAttribute("paging", newPageingVO);


        //시작페이지처리
        int start = (Integer.parseInt(page)-1)*10;
        List<NovelVO> list = masterMapper.masterNovelList(searchType,keyword, sort, start);



        model.addAttribute("list",list);

        //pd픽 관리
        List<PdPickVO> pdPick = masterMapper.pdPickList();
        model.addAttribute("pdPick",pdPick);

        //pd픽 관리
        List<MasterBannerVO> bnPick = masterMapper.bannerPickList();
        model.addAttribute("bnPick",bnPick);


        return "master_novelManage";
    }

    //소설관리 - 배너픽 모달
    @GetMapping("/master/novelManagement/banner")
    public String bannerPick(Model model,
                             HttpSession session,
                             @Param("num")       String num)throws Exception
    {

        //레벨확인
        try                 {level= (Integer)session.getAttribute("U_LEVEL");}
        catch (Exception e) {level = 0;}

        //레벨확인
        if(num == null || num == "") {num    = ""; }

        if (level == 9 && num != ""){
            //커버탐색
            String cover;
            try {cover = masterMapper.bannerPickGet(num);}
            catch (Exception e){cover = "";}


            if(cover != ""){ model.addAttribute("cover",cover); }
            return "image_modalC";
        }else
        {
            return "banner_warnning";
        }
    }

    //배너픽 등록
    @PostMapping("/bannerChange.do")
    @ResponseBody
    public String bannerChange(HttpSession session,
                             @RequestParam(value = "imgfile"    ,required = false) MultipartFile imgfile,
                             @RequestParam(value = "oldCover"   ,required = false) String oldCover,
                             @RequestParam(value = "n_num"   ,required = false) int n_num,
                             MasterMemoVO masterMemoVO,
                             HttpServletRequest request)throws Exception
    {
        String answer="no";

        //기존 커버 존재여부 확인
        int cnt = masterMapper.bannerPickCnt(n_num);

        //신규 이미지 저장
        if (!imgfile.isEmpty())
        {
            //커버존재확인되면 파일 지움
            if (cnt > 0) {fileUploadService.deleteFile(oldCover);}

            //이미지 등록
            answer = fileUploadService.eventFileUpload(imgfile);
        }

        //제대로 저장 되었을 경우
        if (!answer.equals("no")){
            //신규저장
            if(cnt == 0) { int p = masterMapper.bannerPickSet(n_num, answer);}

            //기존수정
            if(cnt >  0) { int p = masterMapper.bannerPickUpdate(n_num, answer);}
        }

        return answer;
    }

    //배너픽 삭제
    @PostMapping("/BannerDelete.do")
    @ResponseBody
    public int masterBannerDelete(HttpSession session,
                                @RequestParam(value = "n_num",required = false) String n_num,
                                HttpServletRequest request)throws Exception
    {
        int answer = 0;
        String cover;
        u_num = "1";

        //커버 존재시 삭제
        try {cover = masterMapper.bannerPickGet(n_num);}
        catch (Exception e) {cover = "none";}
        if (cover != "none") {fileUploadService.deleteFile(cover);}

        //삭제
        try                 {answer = masterMapper.deleteBannerPick(n_num);}
        catch (Exception e) {}

        return answer;
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
        try                 {level= (Integer)session.getAttribute("U_LEVEL");}
        catch (Exception e) {level = 0;}
        if(level != 9 || level == 0){return "redirect:/novelnet";}

        //변수 처리
        if(page == null || page == "0")                     {page    = "1"; }
        else{ if(manageService.isInteger(page) == false)    {page    = "1";}}

        if(searchType == null || searchType.replace(" ","") == "")  {searchType = "all";}
        model.addAttribute("searchType",searchType);

        if(keyword    == null || keyword.replace(" ","")    == "")  {keyword    = "";}

        if(sort == null || sort == "")                      {sort    = "date"; }
        model.addAttribute("sort",sort);


        //페이징처리
        int allPageCnt = masterMapper.userCnt(searchType,keyword,sort);
        pageingService.setNowPage(page);
        pageingService.setTotalCount(allPageCnt);
        newPageingVO = pageingService.setNewPageingVO(newPageingVO);

        model.addAttribute("paging", newPageingVO);


        //시작페이지처리
        int start = (Integer.parseInt(page)-1)*10;
        List<UserVO> list = masterMapper.masterUserList(searchType,keyword,sort,start);



        model.addAttribute("list",list);


        return "master_userManagement";
    }



    //공지사항
    @GetMapping("/master/notification")
    public String notification(Model model,
                                HttpSession session,
                                NewPageingVO newPageingVO,
                                @Param("page")       String page,
                                @Param("carte")      String carte)throws Exception
    {
        //아이디 처리
        try                 {u_num= (String)session.getAttribute("U_NUM").toString();}
        catch (Exception e) {u_num = null;}

        //변수 처리
        if(page == null || page == "0")                     {page    = "1"; }
        else{ if(manageService.isInteger(page) == false)    {page    = "1";}}

        if(carte == null || carte.replace(" ","") == "")  {carte = "gong";}
        model.addAttribute("carte",carte);



        //페이징처리
        int allPageCnt = masterMapper.findGongListCnt(carte);
        pageingService.setNowPage(page);
        pageingService.setTotalCount(allPageCnt);
        newPageingVO = pageingService.setNewPageingVO(newPageingVO);

        model.addAttribute("paging", newPageingVO);

        //시작페이지처리
        int start = (Integer.parseInt(page)-1)*10;
        List<MasterMemoVO> list = masterMapper.findGongList(carte);

        model.addAttribute("list",list);

        //pd픽 관리
        List<PdPickVO> pdPick = masterMapper.pdPickList();
        model.addAttribute("pdPick",pdPick);


        return "master_notification";
    }


    //공지 작성 페이지
    @GetMapping("/master/write")
    public String masterWrite(Model model,
                              @RequestParam(value = "page"       ,required = false) String page,
                              @RequestParam(value = "carte"       ,required = false) String carte,
                               MasterMemoVO masterMemoVO,
                               HttpSession session,
                               NewPageingVO newPageingVO)throws Exception
    {
        //레벨확인
        try                 {level= (Integer)session.getAttribute("U_LEVEL");}
        catch (Exception e) {level = 0;}
        if(level != 9 || level == 0){return "redirect:/master/notification?carte="+carte+"&page="+page;}

        //변수정리
        if(page == null || page == "0")                     {page    = "1"; }
        else{ if(manageService.isInteger(page) == false)    {page    = "1";}}
        if(carte == null || carte.replace(" ","") == "")  {carte = "gong";}

        model.addAttribute("memoVO",masterMemoVO);
        model.addAttribute("type","write");
        model.addAttribute("link","/masterWrite.do");
        model.addAttribute("carte",carte);
        model.addAttribute("page",page);
        return "master_write";

    }

    //공지 작성
    @PostMapping("/masterWrite.do")
    public String masterWrite(HttpSession session,
                              @RequestParam(value = "write_title",required = false) String write_title,
                              @RequestParam(value = "write_memo" ,required = false) String write_memo,
                              @RequestParam(value = "gongType"   ,required = false) String gongType,
                              @RequestParam(value = "page"       ,required = false) String page,
                              @RequestParam(value = "carte"       ,required = false) String carte,
                              @RequestParam(value = "imgfile"    ,required = false) MultipartFile imgfile,
                              MasterMemoVO masterMemoVO,
                              HttpServletRequest request)throws Exception
    {


//        if((String)session.getAttribute("U_LEVEL").toString() == "9"){

            //변수 처리
            if(page == null || page == "0")                     {page    = "1"; }
            else{ if(manageService.isInteger(page) == false)    {page    = "1";}}

            String answer;

            if(write_title.replace(" ","") == "") {write_title = "오늘의 공지";}

            masterMemoVO.setU_num("1");
            masterMemoVO.setMa_title(write_title);
            masterMemoVO.setMa_memo(write_memo);
            masterMemoVO.setMa_type(gongType);

            //이미지 저장
            if (!imgfile.isEmpty())
            {
                answer = fileUploadService.eventFileUpload(imgfile);
                if (answer != "no")
                {
                    masterMemoVO.setMa_cover(answer);
                }
            } //이미지 저장

            int t = masterMapper.writeGongji(masterMemoVO);

//        }
        return "redirect:/master/view?No="+masterMemoVO.getMa_num()+"&carte="+gongType+"&page="+page;
    }

    //공지 수정 페이지
    @GetMapping("/master/rewrite")
    public String masterReWrite(Model model,
                                @RequestParam(value = "No",required = false) int No,
                                @RequestParam(value = "page"       ,required = false) String page,
                                @RequestParam(value = "carte"       ,required = false) String carte,
                                MasterMemoVO masterMemoVO,
                                HttpSession session,
                                NewPageingVO newPageingVO)throws Exception
    {
        //레벨확인
        try                 {level= (Integer)session.getAttribute("U_LEVEL");}
        catch (Exception e) {level = 0;}
        if(level != 9 || level == 0){return "redirect:/master/view?No="+masterMemoVO.getMa_num();}

        //변수정리
        if(page == null || page == "0")                     {page    = "1"; }
        else{ if(manageService.isInteger(page) == false)    {page    = "1";}}
        if(carte == null || carte.replace(" ","") == "")  {carte = "gong";}


        masterMemoVO = masterMapper.findGonjiDate(No);
        model.addAttribute("memoVO",masterMemoVO);
        model.addAttribute("type","rewrite");
        model.addAttribute("link","/masterReWrite.do");
        return "master_write";
    }

    //공지 수정
    @PostMapping("/masterReWrite.do")
    public String masterReWrite(HttpSession session,
                                  @RequestParam(value = "write_title",required = false) String write_title,
                                  @RequestParam(value = "write_memo" ,required = false) String write_memo,
                                  @RequestParam(value = "gongType"   ,required = false) String gongType,
                                  @RequestParam(value = "page"       ,required = false) String page,
                                  @RequestParam(value = "carte"      ,required = false) String carte,
                                  @RequestParam(value = "number"     ,required = false) int number,
                                  @RequestParam(value = "imgfile"    ,required = false) MultipartFile imgfile,
                                  MasterMemoVO masterMemoVO,
                                  HttpServletRequest request)throws Exception
    {
        String answer="no";
        String oldCover;

        if(page == null || page == "0")                     {page    = "1"; }
        else{ if(manageService.isInteger(page) == false)    {page    = "1";}}
        if(carte == null || carte.replace(" ","") == "")  {carte = "gong";}

        try {oldCover = masterMapper.findCover(number);}
        catch (Exception e) {oldCover = "noImg";}

        if(write_title.replace(" ","") == "") {write_title = "오늘의 공지";}

        masterMemoVO.setImageOn("off");
        masterMemoVO.setU_num("1");
        masterMemoVO.setMa_num(number);
        masterMemoVO.setMa_title(write_title);
        masterMemoVO.setMa_memo(write_memo);
        masterMemoVO.setMa_type(gongType);

        //신규 이미지 저장
        if (!imgfile.isEmpty())
        {
            //기존 이미지가 있을경우 삭제 조치
            if (oldCover != "noImg") {fileUploadService.deleteFile(oldCover);}

            //이미지 등록
            answer = fileUploadService.eventFileUpload(imgfile);
            if (answer != "no")
            {
                masterMemoVO.setMa_cover(answer);
                masterMemoVO.setImageOn("on");
            }
        }

        //공지로 바꿀경우 이미지 삭제
        if (gongType == "gong") { if (oldCover != "noImg") {fileUploadService.deleteFile(oldCover);} }

        int t = masterMapper.reWriteGongJi(masterMemoVO);

        return "redirect:/master/view?No="+masterMemoVO.getMa_num()+"&carte="+carte+"&page="+page;
    }

    //공지 삭제
    @PostMapping("/masterMemoDelete.do")
    @ResponseBody
    public int DeleteMasterMemo(HttpSession session,
                                @RequestParam(value = "No",required = false) int ma_num,
                                HttpServletRequest request)throws Exception
    {
        int answer = 0;
        String cover;
        u_num = "1";

//        if((String)session.getAttribute("U_LEVEL").toString() == "9")
//        {
            //커버 존재시 삭제
            try {cover = masterMapper.findCover(ma_num);}
            catch (Exception e) {cover = "none";}
            if (cover != "none") {fileUploadService.deleteFile(cover);}

            //삭제
            try                 {answer = masterMapper.deleteMasterMemo(ma_num,u_num);}
            catch (Exception e) {System.out.println("실패");}
//        }

        return answer;
    }

    //공지 화면 페이지
    @GetMapping("/master/view")
    public String masterView(Model model,
                            @RequestParam(value = "No",required = false) Integer No,
                            @RequestParam(value = "page"       ,required = false) String page,
                            @RequestParam(value = "carte"       ,required = false) String carte,
                            MasterMemoVO masterMemoVO,
                            HttpSession session,
                            NewPageingVO newPageingVO)throws Exception
    {
        if(No == null) {return "redirect:/master/notification?carte="+carte+"&page="+page;}


        if(page == null || page == "0")                     {page    = "1"; }
        else{ if(manageService.isInteger(page) == false)    {page    = "1";}}
        if(carte == null || carte.replace(" ","") == "")  {carte = "gong";}

        masterMemoVO = masterMapper.findGonjiDate(No);
        model.addAttribute("memoVO",masterMemoVO);
        model.addAttribute("page",page);
        model.addAttribute("carte",carte);

        return "master_view";

    }
}
