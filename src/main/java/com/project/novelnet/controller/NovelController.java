package com.project.novelnet.controller;

import com.google.gson.JsonObject;
import com.project.novelnet.Vo.*;
import com.project.novelnet.Vo.MasterVO.MasterBannerVO;
import com.project.novelnet.repository.*;
import com.project.novelnet.service.FileUploadService;
import com.project.novelnet.service.ManageService;
import com.project.novelnet.service.PageingService;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Controller
public class NovelController {

    private final NovelRepository novelRepository;

    public NovelController(NovelRepository novelRepository) {
        this.novelRepository = novelRepository;
    }

    //파일 업로드
    @Autowired
    private FileUploadService fileUploadService;

    //서치매퍼
    @Autowired
    private SearchMapper searchMapper;

    //페이징
    @Autowired
    private PageingService pageingService;

    //편의용
    @Autowired
    private ManageService manageService;

    //경고용 매퍼
    @Autowired
    private WarningMapper warningMapper;

    @Autowired
    private MasterMapper masterMapper;


    //메인페이지
    @GetMapping("/novelnet")
    public String indexPage(HttpSession session,
                            Model model,
                            HttpServletRequest request)throws Exception{
        //조회수, 추천수, 북마크수별로 조회
        List<NovelVO> bestCount     = searchMapper.findBest("count",3);
        List<NovelVO> bestGood      = searchMapper.findBest("best",3);
        List<NovelVO> bestBookmark  = searchMapper.findBest("bookmark",3);

        model.addAttribute("bestCount",bestCount);
        model.addAttribute("bestGood",bestGood);
        model.addAttribute("bestBookmark",bestBookmark);


        //PD픽과 독점작 조회
        //피디픽 설정
        List<Integer> pdlist =  searchMapper.PdPickList();
        Collections.shuffle(pdlist);

        //새 리스트에 마이바티스값 삽입
        List<NovelVO> pickList = searchMapper.findPdPick(pdlist);
        model.addAttribute("pickList", pickList);


        //배너픽 선정
        List<MasterBannerVO> bnlist = masterMapper.bannerRandemSet();
        model.addAttribute("bnlist",bnlist);



        return "index";
    }

    //소설매퍼
    @Autowired
    private NovelMapper novelMapper;

    //글쓰기 페이지
    @GetMapping("/novelnet/write")
    public String novelWrite(HttpSession session, Model model,
                             @RequestParam(value = "n_num"      ,required = false) String n_num)
    {

        if((Integer)session.getAttribute("U_LEVEL") == 1)
        {
            return "userStop";
        }
        else{
            if (n_num == null || session.getAttribute("U_NUM") == null || n_num.matches("-?\\d+(\\.\\d+)?") == false)
            {
                return "redirect:/novelnet";
            }
            else
            {
                if(novelMapper.memoOK((Integer)session.getAttribute("U_NUM"), n_num) == 0) {
                    return "redirect:/novelnet";
                }
            }
            return "novel_write";
        }
    }

    //글작성
    @PostMapping("/novelnet/writeForm")
    public String novelWrite(NovelWriteForm nwForm, HttpSession session)
    {
        MemoVO memoVO = new MemoVO();

        memoVO.setM_title(nwForm.getWrite_title());
        memoVO.setM_memo(nwForm.getWrite_memo());
        memoVO.setM_type(nwForm.getWrite_type());
        memoVO.setN_num(nwForm.getWrite_number());
        novelRepository.memoSave(memoVO);

        return  "redirect:/novelnet/view?n_num="+nwForm.getWrite_number()+"&sort="+nwForm.getSort()
                +"&page="+nwForm.getPage()+"&chapter="+memoVO.getM_num();
    }

    //이미지 업로드 로직. 배포전에 WebMvcConfig를 비롯해 경로수정할것.
    @PostMapping(value = "/novelnet/writeImg.do" ,  produces = "application/json")
    @ResponseBody
    public JsonObject uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {


        JsonObject jsonObject = new JsonObject();

        String fileRoot         = "/home/ubuntu/novelNet/noteImg/";	  //저장될 외부 파일 경로
        String originalFileName = multipartFile.getOriginalFilename();	                                          //오리지날 파일명
        String extension        = originalFileName.substring(originalFileName.lastIndexOf("."));	          //파일 확장자

        String savedFileName    = UUID.randomUUID() + extension;	                                              //저장될 파일 명

        File targetFile         = new File(fileRoot + savedFileName);

        try {
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);	//파일 저장
            jsonObject.addProperty("url", "/noteImg/"+savedFileName);
            jsonObject.addProperty("responseCode", "success");

        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile);	//저장된 파일 삭제
            jsonObject.addProperty("responseCode", "error");
            e.printStackTrace();
        }

        return jsonObject;
    }


    //글수정
    @GetMapping("/novelnet/reWrite")
    public String novelreWrite(HttpSession session, Model model,
                                 @RequestParam(value = "n_num"      ,required = false) String n_num,
                                 @RequestParam(value = "sort"      ,required = false) String sort,
                                 @RequestParam(value = "page"      ,required = false) String page,
                                 @RequestParam(value = "chapter"    ,required = false) String chapter)
    {

        if (page == null || page =="" ||page=="0")
        {
            page = "1";
        }
        if (sort == null || sort =="")
        {
            sort = "asc";
        }
        if (manageService.isInteger(n_num) == false || manageService.isInteger(chapter) == false || chapter == null || n_num ==null || session.getAttribute("U_NUM") == null)
        {
            return "redirect:/novelnet/";
        }

        MemoVO memoVO = novelMapper.getMemo(n_num, chapter);
        if (memoVO.getM_num() == null)
        {
            return "redirect:/novelnet/novel?" +
                    "n_num="+n_num+
                    "&sort="+sort+
                    "&page="+page;
        }

        model.addAttribute("memoVo", memoVO);


        if (n_num == null || session.getAttribute("U_NUM") == null || n_num.matches("-?\\d+(\\.\\d+)?") == false)
        {
            return "redirect:redirect:/novelnet/novel?" +
                    "n_num="+n_num+
                    "&sort="+sort+
                    "&page="+page;
        }
        else
        {
            if(novelMapper.memoOK((Integer)session.getAttribute("U_NUM"), n_num) == 0) {

                return "redirect:redirect:/novelnet/novel?" +
                        "n_num="+n_num+
                        "&sort="+sort+
                        "&page="+page;
            }
        }
        return "novel_reWrite";
    }

    //글작성
    @PostMapping("/novelnet/reWriteForm")
    public String novelreWrite(NovelWriteForm nwForm,
                               HttpSession session)throws Exception
    {
        MemoVO memoVO = new MemoVO();

        String memo = nwForm.getWrite_memo().replaceAll("\\n", "<br/>");
        memoVO.setM_title(nwForm.getWrite_title());
        memoVO.setM_memo(memo);
        memoVO.setM_type(nwForm.getWrite_type());
        memoVO.setM_num(Integer.parseInt(nwForm.getWrite_chapter()));

        novelMapper.memoUpdate(memoVO);

        return "redirect:/novelnet/view?n_num="+nwForm.getWrite_number()+"&chapter="+nwForm.getWrite_chapter()+
               "&sort="+nwForm.getSort()+"&page="+nwForm.getPage();
    }

    //삭제
    @PostMapping("/memoDelete.do")
    @ResponseBody
    public String memoDelete (@RequestParam("chapter") String  chapter,
                              @RequestParam("n_num") String  n_num,
                              HttpSession session)throws Exception
    {
        //1:삭제됨,  2:권한없음,  3:로직에러로 삭제안됨
        String answer = "3";

        //로그인과 경로체크
        if(session.getAttribute("U_NUM") != null)
        {
            String u_num = (String)session.getAttribute("U_NUM").toString();
            int cheak = novelMapper.UpdateOkCheaker(chapter,u_num);
            if(cheak > 0){
                novelMapper.deleteMemo(chapter);
                answer = "1";       //삭제됨
            }
            else {
                answer = "0";       //삭제권한 없음
            }
        }

        //작성자 권한 확인
        return answer;
    }

    //소설작성
    @GetMapping("/novelnet/regist")
    public String novelRegist(HttpSession session)
    {
        if ((Integer)session.getAttribute("U_LEVEL")==1)
        {
            return "userStop";
        }
        else {
            if (session.getAttribute("U_NUM")==null){
                return "redirect:/novelnet";
            }
            else
            {
                return "novel_regist";
            }
        }
    }

    //소설 수정
    @GetMapping("/novelnet/novelUpdate")
    public String novelUpdate(HttpSession session,
                              @RequestParam(value = "n_num",required = false) String n_num,
                              Model model)throws Exception
    {

        //로그인과 경로체크
        if (n_num == null || n_num.matches("-?\\d+(\\.\\d+)?") == false){ return "redirect:/novelnet"; }
//        if (session.getAttribute("U_NUM") == null) { return "redirect:/novelnet/novel?n_num=" + n_num;  }

//        if ((Integer)session.getAttribute("U_LEVEL")==1)
        if (n_num==null)
        {
            return "userStop";
        }else
        {
//            int u_num = (Integer)session.getAttribute("U_NUM");
            int u_num = 1;

            //작성자 권한 확인
            int Cheak = novelMapper.memoOK(u_num, n_num);

            if (Cheak > 0){
                NovelVO novelVO = novelMapper.getBookDate(n_num);
                model.addAttribute("novelVO", novelVO);
            }else
            {
                return "redirect:/novelnet/novel?n_num=" + n_num;
            }

            //태그 체크
            String first_tag  = novelMapper.tags(n_num, "1st").replace("#","");
            String second_tag = novelMapper.tags(n_num, "2st");

            //태그 모델에 등록
            model.addAttribute("firstTag",first_tag);

            if (second_tag != null || second_tag != "")
            {
                model.addAttribute("secondTag",second_tag);
            }

            return "novel_update";
        }
    }

    @PostMapping("/novelnet/novelRegist.do")
    public String novelRegistDo(HttpSession session,
                                @RequestParam(value = "regist_title"      ,required = false) String regist_title,
                                @RequestParam(value = "write_class"       ,required = false) String write_class,
                                @RequestParam(value = "monopoly"          ,required = false) String monopoly,
                                @RequestParam(value = "consummation"      ,required = false) String consummation,
                                @RequestParam(value = "first"             ,required = false) String first,
                                @RequestParam(value = "hashtag_add"       ,required = false) String hashtag_add,
                                @RequestParam(value = "novel_introduction",required = false) String novel_introduction,
                                @RequestParam(value = "coverFile"         ,required = false) MultipartFile coverFile,
                                HttpServletRequest request)throws Exception
    {
        regist_title = regist_title.replace(" ","");
        NovelVO novelVO = new NovelVO();

        if(regist_title != null && !regist_title.equals("") && session.getAttribute("U_NUM") != null)
        {

            novelVO.setN_title(regist_title);               //타이틀
            novelVO.setN_type(write_class);                 //연재방식
            novelVO.setN_monopoly(monopoly);                //독점여부
            novelVO.setN_introduction(novel_introduction);  //소개
            novelVO.setN_fin(consummation);                 //연재상태
            novelVO.setU_num(session.getAttribute("U_NUM").toString()); //작성자

            //저장
            novelMapper.novelRegist(novelVO);

            //이미지 저장
            if (!coverFile.isEmpty()) { fileUploadService.fileUpload(coverFile, novelVO.getN_num()); } //이미지 저장
        }
        else
        {
            return "redirect:/novelnet/regist";
        }

        if (hashtag_add.length()>25)
        {
            hashtag_add = hashtag_add.substring(0,25);
        }
        //Hashtag 저장
        if(first.equals("none") || first == null) { hashtag_add = "기타," + hashtag_add.replace(" ", ""); }
        else{ hashtag_add = first.replace(" ", "") + "," + hashtag_add.replace(" ", ""); }

        if ((hashtag_add != null || !hashtag_add.equals("")) && novelVO.getN_num() != null)
        {
            novelRepository.TagSave(novelVO.getN_num(), hashtag_add);
        }

        return "redirect:/novelnet/novel?n_num="+novelVO.getN_num();
    }


    @PostMapping("/novelnet/novelUpdate.do")
    public String novelUpdateDo(HttpSession session,
                                @RequestParam(value = "regist_title"      ,required = false) String regist_title,
                                @RequestParam(value = "write_class"       ,required = false) String write_class,
                                @RequestParam(value = "monopoly"          ,required = false) String monopoly,
                                @RequestParam(value = "consummation"      ,required = false) String consummation,
                                @RequestParam(value = "first"             ,required = false) String first,
                                @RequestParam(value = "hashtag_add"       ,required = false) String hashtag_add,
                                @RequestParam(value = "novel_introduction",required = false) String novel_introduction,
                                @RequestParam(value = "n_num"             ,required = false) String n_num,
                                @RequestParam(value = "coverFile"         ,required = false) MultipartFile coverFile,
                                HttpServletRequest request)throws Exception
    {
        regist_title = regist_title.replace(" ","");
        NovelVO novelVO = new NovelVO();


        if(regist_title != null && !regist_title.equals("") && session.getAttribute("U_NUM") != null)
        {

            novelVO.setN_title(regist_title);               //타이틀
            novelVO.setN_type(write_class);                 //연재방식
            novelVO.setN_monopoly(monopoly);                //독점여부
            novelVO.setN_introduction(novel_introduction);  //소개
            novelVO.setN_fin(consummation);                 //연재상태
            novelVO.setU_num(session.getAttribute("U_NUM").toString()); //작성자
            novelVO.setN_num(Integer.parseInt(n_num));

            //수정중
            //저장
            novelMapper.novelUpdate(novelVO);

            //이미지 저장
            if (!coverFile.isEmpty()) { fileUploadService.fileUpload(coverFile, novelVO.getN_num()); } //이미지 저장
        }
        else
        {
            return "redirect:/novelnet/regist";
        }

        novelMapper.deleteTags(n_num);
        //Hashtag 저장
        if(first.equals("none") || first == null) { hashtag_add = "기타," + hashtag_add.replace(" ", ""); }
        else{ hashtag_add = first.replace(" ", "") + "," + hashtag_add.replace(" ", ""); }

        if ((hashtag_add != null || !hashtag_add.equals("")) && novelVO.getN_num() != null)
        {
            novelRepository.TagSave(novelVO.getN_num(), hashtag_add);
        }

        return "redirect:/novelnet/novel?n_num="+n_num;
    }

    @PostMapping("noveDelete.do")
    @ResponseBody
    public int noveDeleteDo(HttpSession session,
                               NovelVO novelVO,
                                @RequestParam(value = "n_num"             ,required = false) String n_num,
                                HttpServletRequest request)throws Exception
    {
        novelVO = novelMapper.getBookDate(n_num);
        String u_num = (String)session.getAttribute("U_NUM").toString();

        int NovelOk = 0;
        int memoOk=0;
        if (u_num.equals(novelVO.getU_num())){
            novelMapper.deleteTags(n_num);
            NovelOk = novelMapper.deleteNovel(n_num);
            memoOk = novelMapper.deleteAllmemo(n_num);
            fileUploadService.deleteFile(novelVO.getN_cover());
        }

        return NovelOk;
    }


    //글보기 - 게시글확인
    @GetMapping("/novelnet/view")
    public String novelView(HttpSession session,
                            Model model,
                            @RequestParam(value = "n_num",  required = false) String n_num,
                            @RequestParam(value = "sort",   required = false) String sort,
                            @RequestParam(value = "page",   required = false) String page,
                            @RequestParam(value = "chapter",required = false) String m_num,
                            @RequestParam(value = "type",   required = false) String type,
                            HttpServletRequest request)throws Exception{

        if (page == null || manageService.isInteger(page) == false || page=="0" || page=="")
        {
            page = "1";
        }
        if (sort == null || sort =="")
        {
            sort = "asc";
        }
        if (type == null || type =="")
        {
            type = "ep";
        }

        //유저 인증
        String level;
        try { level = (String)session.getAttribute("U_LEVEL").toString();}
        catch (Exception e)     {return "redirect:/novelnet/novel?" +
                                "n_num="+n_num+
                                "&sort="+sort+
                                "&page="+page;}
        if (level != null){
            model.addAttribute("U_level",level);
        }

        int stop = novelMapper.novelStopCheak(n_num);
        if (stop > 0)        { return "redirect:/novelnet";}

        if (m_num == null || manageService.isInteger(m_num) == false || n_num == null || manageService.isInteger(n_num) == false)
        {
            return "redirect:/novelnet";
        }
        else
        {
            //게시물 존재확인
            int memoCnt = novelMapper.cheakMemo(m_num);

            if(memoCnt == 0)
            {
                return "redirect:/novelnet/novel?" +
                        "n_num="+n_num+
                        "&sort="+sort+
                        "&page="+page;
            }

            MemoVO memoVO = novelMapper.getMemo(n_num, m_num);

            //다음화
            String next = novelMapper.getNextMemo(n_num, m_num);
            if (next != null)
            {
                next = "/novelnet/view?n_num="+n_num+
                                        "&sort="+sort+
                                        "&page="+page+
                                        "&chapter="+next;

            }

            //이전화
            String back = novelMapper.getBackMemo(n_num, m_num);
            if (back !=null)
            {
                back = "/novelnet/view?n_num="+n_num+
                                        "&sort="+sort+
                                        "&page="+page+
                                        "&chapter="+back;
            }

            //리플 갯수
            int cnt = novelMapper.getReplyCount(m_num, n_num);

            //리플 목록
            List<ReplyVO> replyVOList = novelMapper.getReply(m_num, n_num);

            //회차리스트
            List<MemoVO> memoVOList = novelMapper.getEpMemo(n_num);

            //읽은 기록 등록
            String u_num;
            if (session.getAttribute("U_NUM") != null)
            {
                u_num = (String)session.getAttribute("U_NUM").toString();

                //읽은 기록 등록
                novelMapper.setRecord(n_num,m_num,u_num);

                //수정권환
                int unt = novelMapper.UpdateOkCheaker(m_num, u_num);
                model.addAttribute("updateOK",unt);


                //조회수증가
                if(session.getAttribute("chapter_"+m_num) == null){
                    session.setAttribute("chapter_"+m_num, m_num);
                    novelMapper.countUp(m_num);

                }
            }

            model.addAttribute("memoVO",memoVO);
            model.addAttribute("next",next);
            model.addAttribute("back",back);
            model.addAttribute("m_num",m_num);
            model.addAttribute("n_num",n_num);
            model.addAttribute("page",page);
            model.addAttribute("sort",sort);
            model.addAttribute("ReplyCnt",cnt);

            model.addAttribute("replyVOList", replyVOList);
            model.addAttribute("memoVOList", memoVOList);

        }
        return "viewer";
    }

    //댓글 등록
    @PostMapping("/replyWrite.do")
    @ResponseBody
    public String replyWriteForim(HttpSession session,
                                  @RequestParam(value = "reply_memo", required = false) String r_memo,
                                  @RequestParam(value = "m_num", required = false) String m_num,
                                  @RequestParam(value = "n_num", required = false) String n_num,
                                  @RequestParam(value = "r_rnum", required = false) String r_rnum,
                                  HttpServletRequest request)throws Exception
    {
        String state = "";
        String u_num;
        if(session.getAttribute("U_NUM") == null)
        {
            return "댓글 작성 실패";
        }
        else
        {
            u_num = (String)session.getAttribute("U_NUM").toString(); //유저번호
        }

        if(r_memo != "" && r_memo != null){
            if (r_rnum == null || r_rnum == "0"){
                novelMapper.saveReply(u_num, n_num, m_num, r_memo);

                state = "댓글 등록완료";
            }else
            {
                novelMapper.saveReReply(u_num, n_num, m_num, r_rnum, r_memo);
                state = "댓글 등록완료";
            }
        }
        return state;
    }

    //댓글삭제
    @PostMapping("/replyDelete.do")
    @ResponseBody
    public String replyDelete(HttpSession session,
                             @RequestParam(value = "r_num", required = false) String r_num,
                             HttpServletRequest request)throws Exception
    {
        String id;
        try                 { id = (String)session.getAttribute("U_NUM").toString();}
        catch (Exception e) { return "fail";}

        if (id != null)
        {
            int ok = novelMapper.deleteMyReply(r_num, id);

            if (ok >0){ return "ok"; }
            else      { return "fail";}
        }
        else
        {
            return "fail";
        }
    }

    //댓글수정
    @PostMapping("/replyUpdate.do")
    @ResponseBody
    public String replyUpdate(HttpSession session,
                              @RequestParam(value = "r_num", required = false) String r_num,
                              @RequestParam(value = "reply_memo", required = false) String r_memo,
                              HttpServletRequest request)throws Exception
    {
        String id;
        try                 { id = (String)session.getAttribute("U_NUM").toString();}
        catch (Exception e) { return "fail";}

        if (id != null)
        {
            int ok = novelMapper.UpdateMyReply(r_num, r_memo, id);

            if (ok >0){ return "ok"; }
            else      { return "fail";}
        }
        else
        {
            return "fail";
        }
    }


    //댓글 신고
    @PostMapping("/stopReply.do")
    @ResponseBody
    public int stopReply(HttpSession session,
                         @RequestParam(value = "r_num", required = false) String r_num,
                         HttpServletRequest request)throws Exception
    {
        int cnt = 0;    //0문제없음. 1이미신고되어있음. 2신고성공함
        String id = (String)session.getAttribute("U_NUM").toString();

        if(id == null && r_num != null) {
            return cnt;
        }
        else
        {
            cnt = novelMapper.replyWarningCheak(r_num,id);
            if(cnt == 0)
            {
                novelMapper.replyWarning(r_num,id);
                cnt = 2;
                return cnt;
            }
            else
            {
                return cnt;
            }
        }
    }

    //댓글 추천 비추
    @PostMapping("/updownlike.do")
    @ResponseBody
    public int updownlike(HttpSession session,
                         @RequestParam(value = "r_num", required = false) String r_num,
                         @RequestParam(value = "type", required = false) String type,
                         HttpServletRequest request)throws Exception
    {
        if(session.getAttribute("U_NUM")!=null){

        }

        int cnt = 0;


        if (type.equals("good"))
        {
            novelMapper.replyGoodUp(r_num);
            cnt = 1;

        }
        if (type.equals("bad"))
        {
            novelMapper.replyBadUp(r_num);
            cnt = 1;
        }

        return cnt;
    }

    //게시글 추천, 비추천
    @PostMapping("/goodUpDown.do")
    @ResponseBody
    public int goodUpDown(HttpSession session,
                              @RequestParam(value = "m_num", required = false) String m_num,
                              @RequestParam(value = "updown", required = false) String updown,
                              HttpServletRequest request)throws Exception
    {
        int m_count;
        if (m_num == null || m_num =="" && updown == null || updown ==""){ m_count=0; }
        else {
            if (updown.equals("up")) {
                novelMapper.goodUp(m_num);
            }
            if (updown.equals("down")) {
                novelMapper.goodDown(m_num);
            }
            m_count = novelMapper.getGoodCount(m_num);
        }

        return m_count;
    }

    //게시물 신고
    @PostMapping("/memoWarning.do")
    @ResponseBody
    public String memoWarning(HttpSession session,
                              @RequestParam(value = "chapter", required = false) String chapter,
                              @RequestParam(value = "n_num", required = false) String n_num,
                              @RequestParam(value = "w_why", required = false) String w_why,
                              HttpServletRequest request)throws Exception
    {
        int u_num;
        int novel_num;
        int memo_num;
        if (w_why == null){ w_why = ""; }

        //0 : 실패,   1:성공,   2:신고한지 24시간이 안됨
        String state = "0";


        if(session.getAttribute("U_NUM") != null) {
            u_num = (Integer)session.getAttribute("U_NUM");

            if(manageService.isInteger(chapter) == true && manageService.isInteger(n_num) ==true)
            {
                novel_num = Integer.parseInt(n_num);
                memo_num  = Integer.parseInt(chapter);

                //신고 등록 여부 확인
                String timgDiff = warningMapper.cheakMemoWarning(novel_num,u_num);

                //신고시간이 null값이거나 1440분을 넘으면 경고등록
                if (timgDiff == null)
                {
                    warningMapper.setMemoWarning(novel_num,u_num,memo_num,w_why);
                    state = "1";
                }
                else
                {
                    if(Integer.parseInt(timgDiff) > 1440)
                    {
                        warningMapper.setMemoWarning(novel_num,u_num,memo_num,w_why);
                        state = "1";
                    }
                    else {state = "2";}
                }
            }
        }

        return state;
    }

    //글보기
    @GetMapping("/novelnet/novel")
    public String novelFront(HttpSession session,
                             Model model,
                             @RequestParam(value = "n_num"      ,required = false) String n_num,
                             @RequestParam(value = "sort"       ,required = false) String sort,
                             @RequestParam(value = "page"       ,required = false) String page,
                             HttpServletRequest request)throws Exception
    {
        int start = 0;  //불러올 게시물 시작번호
        int count = 10; //불러올 게시물 갯수
        String u_num = "0";

        //Null값 처리
        if (page == null || page =="" || page.equals("0") || page.matches("-?\\d+(\\.\\d+)?") == false)
        {
            page = "1";
        }
        if (sort == null || !sort.equals("desc"))
        {
            sort = "asc";
        }

        if(session.getAttribute("U_NUM") != null) {
            u_num = (String) session.getAttribute("U_NUM").toString(); //유저번호
        }


        if (n_num == null || n_num.matches("-?\\d+(\\.\\d+)?") == false ){ return "redirect:/novelnet"; } //소설데이터 검색 실패시
        else
        {
            int stop = novelMapper.novelStopCheak(n_num);
            if (stop > 0)        { return "redirect:/novelnet";}

            //소설 데이터 검색
            System.out.println("책 번호 : " + n_num);
            NovelVO novelVO = new NovelVO();

            //존재여부 체크
            if(novelMapper.cheakBookDate(n_num) == 1)
            {
                try
                {
                    novelVO = novelMapper.getBookDate(n_num); //정보검색

                    //정지여부 확인 후 검색 속행
                    if (novelVO.getStopPoint() ==0)
                    {
                        int pageInt = Integer.parseInt(page); //페이지계산용

                        //페이징
                        PageingVO pageingVO = new PageingVO();
                        model.addAttribute("paging", novelRepository.novelPaging(n_num, pageInt));

                        if (page != "1") {
                            start = (pageInt - 1) * 10;
                        }

                        //게시물 검색
                        List<MemoVO> memoVOList = novelMapper.getMemoDate(n_num, u_num, start, count, page, sort);

                        //북마크여부
                        int bookmark = searchMapper.cheakBookMark(u_num, n_num);

                        //작가검색
                        String writer = novelMapper.getWriter(novelVO.getU_num());
                        if (writer == null || writer == "") {
                            writer = "가입번호 " + novelVO.getU_num() + " 작가님";
                            model.addAttribute("writer", writer);
                        } else {
                            model.addAttribute("writer", writer);
                        }

                        //태그검색
                        List<TagVO> tagVOList = novelMapper.getAllTag(n_num);
                        if (tagVOList != null) {
                            model.addAttribute("tag", tagVOList);
                        }

                        //마지막으로 본 게시물 검색
                        String lastChapter = novelMapper.getLastChapter(n_num, u_num);
                        model.addAttribute("lastChpater", lastChapter);

                        //모델에 뷰로 담을 정보 전송
                        model.addAttribute("memoVOList", memoVOList);
                        model.addAttribute("novelVO", novelVO);
                        model.addAttribute("sort", sort);//정렬
                        model.addAttribute("nowPage", page);//페이지
                        model.addAttribute("bookmark", bookmark);

                        //작가의 다른 소설 검색
                        int t = novelMapper.getAnotherBookCnt(novelVO.getU_num());
                        NovelVO subNovel = new NovelVO();
                        List<TagVO> subTagList = null;

                        //다른소설 존재함
                        if (t >1)
                        {
                            //다른 소설 검색
                            subNovel = novelMapper.getAnotherBook(n_num, novelVO.getU_num());
                            //태그검색
                            subTagList = novelMapper.getMiniTag(subNovel.getN_num());
                        }else
                        {
                            subNovel.setN_num(0);
                            subNovel.setN_cover("");
                            subNovel.setN_title("");
                        }
                        model.addAttribute("subtag", subTagList);
                        model.addAttribute("subNovel", subNovel);


                        return "book_info";
                    }else
                    {
                        return "redirect:/novelnet";
                    }
                }
                catch (Exception e) { e.printStackTrace(); }
            }
            else {
                return "redirect:/novelnet";
            }
        }
        return "book_info";
    }

    @PostMapping("bookmark.do")
    @ResponseBody
    public String bookmarkSwitch(@RequestParam(value = "n_num",required = false) String n_num,
                                 @RequestParam(value = "book_switch",required = false) String book_switch,
                                 HttpSession session) throws Exception
    {
        String bookmarkSwitch = "none";
        String id;
        if(session.getAttribute("U_NUM")!=null)
        {
            id = (String)session.getAttribute("U_NUM").toString();


            if (book_switch.equals("0"))
            {
                int t = searchMapper.cheakBookMark(id, n_num);
                if(t==0)
                {
                    searchMapper.setBookMark(id, n_num);
                    bookmarkSwitch = "on";

                }
            }
            if (book_switch.equals("1"))
            {
                int bm_num = searchMapper.getBookMarkNum(id, n_num);
                searchMapper.deleteBookMark(bm_num);
                bookmarkSwitch = "off";
            }
        }

        return bookmarkSwitch;
    }

    @PostMapping("deleteBookMark.do")
    @ResponseBody
    public String deleteBookMark(@RequestParam(value = "n_num",required = false) String n_num,
                                 HttpSession session) throws Exception
    {
        String message = "noLogin";
        if(session.getAttribute("U_NUM")!=null && n_num!= null)
        {
            String id = (String)session.getAttribute("U_NUM").toString();

            int bm_num = searchMapper.getBookMarkNum(id, n_num);
            searchMapper.deleteBookMark(bm_num);
            message = "deleteOk";
        }

        return message;
    }


    //내가 북마크한 글
    @GetMapping("/novelnet/mybook")
    public String mybook(HttpSession session,
                         @RequestParam(value = "keyword"   ,required = false) String keyword,
                         @RequestParam(value = "newOld"    ,required = false) String newOld,
                         @RequestParam(value = "category" ,required = false) String category,
                         @RequestParam(value = "page"      ,required = false) String page,
                         Model model) throws Exception{

        String u_num="0";

        if(session.getAttribute("U_NUM") != null)
        {
            //유저번호
            u_num = (String) session.getAttribute("U_NUM").toString();
        }


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


        if(page    == null || page == "0" ||page==""){page    = "1";   }
        else{
          if(manageService.isInteger(page) == false){page = "1"; }
        }


        if (u_num != ""){
            //북마크 갯수 확보
            int count = searchMapper.bookmarkCount(u_num, keyword, category);

            //페이징 처리
            pageingService.setNowPage(page);
            pageingService.setTotalCount(count);

            int allPage     = pageingService.getAllPage();
            int nowCase     = pageingService.getNowCase();
            int allCase     = pageingService.getAllCase();
            int leftPage    = pageingService.getLeftPage();
            int rightPage   = pageingService.getRightPage();
            int displayPage = pageingService.getDisplayPage();


            model.addAttribute("allPage", allPage);
            model.addAttribute("nowCase", nowCase);
            model.addAttribute("allCase", allCase);
            model.addAttribute("leftPage", leftPage);
            model.addAttribute("rightPage", rightPage);
            model.addAttribute("displayPage", displayPage);

            //시작페이지처리
            int start = (Integer.parseInt(page)-1)*10;

            //검색(유저번호, 검색어, 업데이트 순서, 완결여부)
            List<NovelVO> novelList = searchMapper.getBookmarkList(u_num, keyword, newOld, category, start);
            
            model.addAttribute("novelList",novelList);

            //년도확인
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            model.addAttribute("year",year);

            //신규냐 오래된순이냐
            model.addAttribute("newOld",newOld);
        }

        //해야할것
        //1.페이징처리
        //2.다음화 여부 확인


        return "mybook";
    }

    @GetMapping("/novelnet/best")
    public String bestPic(HttpSession session,
                          @RequestParam(value = "sort",   required = false) String sort,
                          @RequestParam(value = "carte"   ,required = false) String carte,
                          @RequestParam(value = "page"    ,required = false) String page,
                          Model model) throws Exception
    {
        //정렬
        if (sort == null || sort =="") { sort = "n_good"; }
        else if (sort == "n_count")    { sort = "n_count";}
        else                           { sort = "n_good"; }

        //카테고리
        if(carte == null)                                { carte = ""; }

        //페이지 처리
        if (page == null || page =="" || page.equals("0") || page.matches("-?\\d+(\\.\\d+)?") == false)
        {
            page = "1";
        }
        System.out.println(page);

        //시작페이지
        int start = 0;
        if(manageService.isInteger(page) == true)        { start = (Integer.parseInt(page)-1)*100;}

        //데이터 검색
        List<NovelVO> novelVOList = searchMapper.bestNovelFinder(sort, carte, start);
        int next = searchMapper.bestNovelCount(sort, carte, start+100);

        if (next > 0) {model.addAttribute("next", "ok");}
        else          {model.addAttribute("next", "stop");}

        //다음 단추 표시 여부
        int bottom = 0;
        if (page == "0" && next == 0) {bottom = 1;}

        model.addAttribute("novelVOList", novelVOList);
        model.addAttribute("bottom",bottom);

        return "best";
    }

    @GetMapping("")
    public String t(){
        return "redirect:/novelnet";
    }

    @GetMapping("pro")
    public String pro(){

// String n1 = "안녕하세요 독자여러분.\n"+ "다름이 아니라 개인적인 사정이 생겨 이번 한주동안 잠시 휴재를 갖도록 해야할 것 같습니다.\n" + "한주 충전후 다시 돌아오겠습니다!";
// String n2 ="안녕핫게요 독자여러분. 작가입니다\n" +"다름이 아니라 현재 슬럼프가와서 글을 대대적으로 고칠까 고민중입니다\n" + "이런 소식을 전해드리게되어 대단히 죄솝합니다";
// String n3 = "안녕하세요 불초 작가입니다\n"+"현재 이벤트 중이오니 댓글을 달아주시면 추첨을 통해 기프티콘을 보내드립니다";

//        String A = "";
//        String B = "";
//        String D = "";
//        String type = "";
//        String fin = "";
//        String tagA = "";
//        String tagB = "";
//        String tagC = "";
//        String cover = "";
//        int t = 1;
        Random rand = new Random();

        int d=1;
        int gd;
        int PT;
        String epsord = "";
//        for(int n =1 ; n<=500; n++)
//        {
//            int overPT   = rand.nextInt(3);
//
//            for(int i =0 ; i<=overPT; i++)
//            {
//                PT   = rand.nextInt(2)+1;
//
//                switch (d) {
//                    case 1:
//                        novelMapper.testeM("공지사항입니다",n1,n,0, 0);
//                        d=2;
//                        break;
//                    case 2:
//                        novelMapper.testeM("새로운 전달사항입니다",n2,n,0, 0);
//                        d=3;
//                        break;
//                    default:
//                        novelMapper.testeM("공지확인 부탁드립니다",n3,n,0, 0);
//                        d=1;
//                        break;
//                }
//            }
//        }


//        for(int i = 1; i<=500; i++) {
//            cover = t+".jpg";
//            t++;
//            if (t > 105) {t=1;}
//
//            novelMapper.coverd(cover, i);
//
//            //해쉬태그 제조기
//            int PT = rand.nextInt(8)+1;
//            switch (PT) {
//                case 1:
//                    tagA = "#게이트";
//                    break;
//                case 2:
//                    tagA = "#나작소";
//                    break;
//                case 3:
//                    tagA = "#BL";
//                    break;
//                case 4:
//                    tagA = "#하렘";
//                    break;
//                case 5:
//                    tagA = "#퓨전";
//                    break;
//                case 6:
//                    tagA = "#사극";
//                    break;
//                case 7:
//                    tagA = "#중세";
//                    break;
//                case 8:
//                    tagA = "#로망";
//                    break;
//                default:
//                    tagA = "#고구마";
//                    break;
//            }
//            novelMapper.testeB(tagA,i);
//
//            int PA = rand.nextInt(8)+1;
//            switch (PA) {
//                case 1:
//                    tagB = "#badass";
//                    break;
//                case 2:
//                    tagB = "#쾌활한";
//                    break;
//                case 3:
//                    tagB = "#마법";
//                    break;
//                case 4:
//                    tagB = "#중세";
//                    break;
//                case 5:
//                    tagB = "#선협";
//                    break;
//                case 6:
//                    tagB = "#가족애";
//                    break;
//                case 7:
//                    tagB = "#힘숨찐";
//                    break;
//                case 8:
//                    tagB = "#수인";
//                    break;
//                default:
//                    tagB = "#가상현실";
//                    break;
//            }
//            novelMapper.testeB(tagB,i);
//
//            int PQ = rand.nextInt(8)+1;
//            switch (PQ) {
//                case 1:
//                    tagC = "#판타지";
//                    break;
//                case 2:
//                    tagC = "#무협";
//                    break;
//                case 3:
//                    tagC = "#현대";
//                    break;
//                case 4:
//                    tagC = "#로맨스";
//                    break;
//                case 5:
//                    tagC = "#대체역사";
//                    break;
//                case 6:
//                    tagC = "#공포";
//                    break;
//                case 7:
//                    tagC = "#SF";
//                    break;
//                case 8:
//                    tagC = "#스포츠";
//                    break;
//                default:
//                    tagC = "#기타";
//                    break;
//            }
//            novelMapper.testeA(tagC,i);
//
            //본문
        String A = "";
        for(int i = 1; i<=400; i++){
            int PP = rand.nextInt(12) + 1;
            switch (PP) {
                case 1:
                    A = "<p>암흑가의 검귀.</p> <br>" +
                            "...그리고, 아카데미의 기간 강사.</p>";
                    break;
                case 2:
                    A = "<p>세 번 죽었고.</p>" +
                            "<br>" +
                            "<p>네 번 살았으면.</p>" +
                            "<br>" +
                            "<p>고생했다, 이젠 끝낼 때도 됐다.</p>";
                    break;
                case 3:
                    A = "<p>거대 용병 기업으로 거듭난 마계.</p>" +
                            "<br>" +
                            "<p>그 소소한 이야기.</p>";
                    break;
                case 4:
                    A = "<p>정신차려보니 이상한 호텔에 떨어졌다.</p>" +
                            "<br>" +
                            "<p>무슨 일이 일어난걸까?</p>";
                    break;
                case 5:
                    A = "<p>어쩌다 불로불사가 돼서,</p><br>" +
                            "<p>어쩌다 아이를 키우는 이야기</p>";
                    break;
                case 6:
                    A = "<p>1회차는 게임이었지만,</p>" +
                            "<br>" +
                            "<p>2회차는 실전이라고?!</p>" +
                            "<br>" +
                            "<p>하지만 오히려 좋아.</p>" +
                            "<br>" +
                            "<p>1회차 때 구하지 못한 나의 사랑스러운 최애캐이자, 연인이었던 그녀를 구해서 반드시 결혼해주마!</p>"+
                            "<br>" +
                            "<p>그러기 위해서는 세계부터 구해야겠지?</p>" +
                            "<br>" +
                            "<p>그런데 여자가 좀 많은데?</p>" +
                            "<br>" +
                            "<p>여친이 하렘을 강요하는데?!</p>";
                    break;
                case 7:
                    A = "<p>즐겨하던 게임 지옥 난이도에 최약체로 빙의했다. 살고 싶은데 주인공 새끼 컨트롤 상태가 심각하다.<br></p>" +
                            "<br>" +
                            "<p>어쩔 수 없다.<br></p>" +
                            "<br>" +
                            "<p>내가 직접 배드 엔딩을 막는 수밖에.</p>";
                    break;
                case 8:
                    A = "<p>24시간 풀접속 게임 폐인 쌀먹녀를 주웠다.<br></p>" +
                            "<br>" +
                            "<p>그런데... 행복이 과하다.</p>";
                    break;
                case 9:
                    A = "무료분만 찍먹하고 뱉었던 로판에 빙의했다.<br></p>" +
                            "<p>다행히 빙의한 몸 속에 흐르는 피가 푸르고도 푸르러 먹고 사는 것에 문제는 없었다.</p>" +
                            "<br>" +
                            "<p>우리 가문의 영화는 황실로부터 대대로 받은 은덕 덕분이다.</p>" +
                            "<br>" +
                            "<p>그 대가인지 제도 광장에서 황제 초상화 좀 열심히 흔들 것 같은 아버지를 두었고, 강제로 공무원 루트를 밟게 되었다.</p>" +
                            "<br>" +
                            "<p>남들 로맨스 찍을 때 나는 싱글벙글 공무원 라이프를 보낸다.</p>";
                    break;
                case 10:
                    A = "<p>10년간 플레이해왔던 게임의 후속작 베타테스트 신청 메일에 낚여서 끌려갔는데, 지금껏 깔아온 모드가 그대로 남아있었다.</p>" +
                            "<br>" +
                            "<p>적 강화 모드는 물론, NPC의 성별 반전 모드와 외형 리터칭 모드까지 전부 다.</p>" +
                            "<br>" +
                            "<p>심지어는 캐릭터들의 복장 변경 모드까지도.</p>";
                    break;
                case 11:
                    A = "[창술 Lv.1]</p>" +
                            "<br>" +
                            "<p>많은 특성 중에서 창술이 걸렸다는 건 어떻게 보면 나쁘지 않은 결과로 볼 수 있지만…….</p>" +
                            "<br>" +
                            "<p>아니, 나빴다.</p>" +
                            "<br>" +
                            "<p>그것도 내가 느끼기엔 거의 최악의 결과였다.</p>";
                    break;
                case 12:
                    A = "<p>진달래는 1998년 1월 5일부터 동월 26일까지 방영되었던 KBS 2TV 월화 드라마이며, 총 8부작이다. 극본은 윤혁민 외 2명, 연출은 홍성덕 PD, 무술감독은 김백수, 무용총괄은 김향금[, 음악은 구훈과 이임우, 편곡은 박찬혁 등이 맡았다.</p>" +
                            " <br>" +
                            "<p> 라는 스토리를 나는 기억하고 있다</p>";
                    break;
                default:
                    A = "<p>야겜에 나오는 시간 정지 교배 아저씨가 되어버렸다.</p>" +
                            "<br>" +
                            "<br>" +
                            "<p>근데 상황이 개판 5분 전이라, 먼저 세상을 구했더니.</p>" +
                            "<br>" +
                            "<br>" +
                            "<p>어느새, 주변에서 나를 영웅이라고 칭송하고 있었다.</p>" +
                            "<br>" +
                            "<br>" +
                            "<p>……영웅이고 나발이고, 그냥 은퇴하고 싶다.</p>";
                    break;
            }
            novelMapper.introdu(A, i);
        }
//
//            int PC = rand.nextInt(31)+1;
//            switch (PC) {
//                case 1:
//                    B = "오늘";
//                    break;
//                case 2:
//                    B = "내가";
//                    break;
//                case 3:
//                    B = "아카데미";
//                    break;
//                case 4:
//                    B = "오후의";
//                    break;
//                case 5:
//                    B = "하늘의";
//                    break;
//                case 6:
//                    B = "전능의";
//                    break;
//                case 7:
//                    B = "구라안치고";
//                    break;
//                case 8:
//                    B = "엑스트라";
//                    break;
//                case 9:
//                    B = "천만의";
//                    break;
//                case 10:
//                    B = "강호";
//                    break;
//                case 11:
//                    B = "무협지";
//                    break;
//                case 12:
//                    B = "평범한";
//                    break;
//                case 13:
//                    B = "SSS급";
//                    break;
//                case 14:
//                    B = "히로인은";
//                    break;
//                case 15:
//                    B = "호랑";
//                    break;
//                case 16:
//                    B = "밀밭의";
//                    break;
//                case 17:
//                    B = "죽음의";
//                    break;
//                case 18:
//                    B = "불사";
//                    break;
//                case 19:
//                    B = "6★";
//                    break;
//                case 20:
//                    B = "히든";
//                    break;
//                case 21:
//                    B = "버튜얼";
//                    break;
//                case 22:
//                    B = "내 옆방의";
//                    break;
//                case 23:
//                    B = "찐따가";
//                    break;
//                case 24:
//                    B = "천상천하";
//                    break;
//                case 25:
//                    B = "모바일";
//                    break;
//                case 26:
//                    B = "FFF급";
//                    break;
//                case 27:
//                    B = "듣보잡";
//                    break;
//                case 28:
//                    B = "세계를 멸망시키는";
//                    break;
//                case 29:
//                    B = "거짓말쟁이의";
//                    break;
//                case 30:
//                    B = "아메리카의";
//                    break;
//                default:
//                    B = "내 나라의";
//                    break;
//            }
//
//            int PF = rand.nextInt(20)+1;
//            switch (PF) {
//                case 1:
//                    D = " 창병이 되다";
//                    break;
//                case 2:
//                    D = " 엘리트 지휘관";
//                    break;
//                case 3:
//                    D = " 재능 천재";
//                    break;
//                case 4:
//                    D = " 혼자 레벨업 하는 주인공";
//                    break;
//                case 5:
//                    D = " 검객";
//                    break;
//                case 6:
//                    D = " 버튜버가 되다";
//                    break;
//                case 7:
//                    D = " 가짜 주인공이다";
//                    break;
//                case 8:
//                    D = " 꿀빠는 악역";
//                    break;
//                case 9:
//                    D = " 헌터 라이프";
//                    break;
//                case 10:
//                    D = " 방랑기";
//                    break;
//                case 11:
//                    D = " 추방 용사";
//                    break;
//                case 12:
//                    D = " 반 맨 뒤 4번째 자리";
//                    break;
//                case 13:
//                    D = " 폐기물이 되다";
//                    break;
//                case 14:
//                    D = " 나홀로 독점";
//                    break;
//                case 15:
//                    D = " 첩자";
//                    break;
//                case 16:
//                    D = " 아스라이";
//                    break;
//                case 17:
//                    D = " 네크로멘서가 되다";
//                    break;
//                case 18:
//                    D = " 메이지";
//                    break;
//                case 19:
//                    D = " 조각사";
//                    break;
//                default:
//                    D = " 총통이 되다";
//                    break;
//            }
//
//            int RF = rand.nextInt(1)+1;
//            switch (RF) {
//                case 1:
//                    type = "prime";
//                    break;
//                default:
//                    type = "free";
//                    break;
//            }
//
//
//            int RK = rand.nextInt(3)+1;
//            switch (RK) {
//                case 1:
//                    fin = "done";
//                    break;
//                default:
//                    fin = "doing";
//                    break;
//            }
//            novelMapper.tested(B+D,A,type, fin, i);
//        }
        return "test";
    }
}
