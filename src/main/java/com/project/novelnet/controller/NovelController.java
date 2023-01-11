package com.project.novelnet.controller;

import com.google.gson.JsonObject;
import com.project.novelnet.Vo.*;
import com.project.novelnet.repository.NovelMapper;
import com.project.novelnet.repository.NovelRepository;
import com.project.novelnet.repository.SearchMapper;
import com.project.novelnet.service.FileUploadService;
import com.project.novelnet.service.ManageService;
import com.project.novelnet.service.PageingService;
import org.apache.commons.io.FileUtils;
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
        //리스트 생성
        List<String> pdlist = new ArrayList<>();

        //PD픽 리스트 가져오기
        String pdnovel = searchMapper.PdPickList();

        //리스트 잘러서 배열 넣기
        String[] splitPick = pdnovel.split("/");
        for (int i=0; i< splitPick.length; i++)  {pdlist.add(splitPick[i]);}

        //새 리스트에 마이바티스값 삽입
        List<NovelVO> pickList = searchMapper.findPdPick(pdlist);
        model.addAttribute("pickList", pickList);
        System.out.println(pickList);

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
        System.out.println("유저번호 :"+session.getAttribute("U_NUM") +  "\n 소설번호 : " + n_num);


        if (n_num == null || session.getAttribute("U_NUM") == null || n_num.matches("-?\\d+(\\.\\d+)?") == false)
        {
            return "redirect:/novelnet";
        }
        else
        {
            if(novelMapper.memoOK((Integer)session.getAttribute("U_NUM"), n_num) == 0) {
                System.out.println("작성권한이 없습니다.");
                return "redirect:/novelnet";
            }
        }
        return "novel_write";
    }

    //글작성
    @PostMapping("/novelnet/writeForm")
    public String novelWrite(NovelWriteForm nwForm, HttpSession session)
    {
        MemoVO memoVO = new MemoVO();
        for (int i = 0; i<40 ; i++)
        {
            memoVO.setM_title(nwForm.getWrite_title()+" "+i+"번째");
            memoVO.setM_memo(nwForm.getWrite_memo()+" "+i+"번째");
            memoVO.setM_type(nwForm.getWrite_type());
            memoVO.setN_num(nwForm.getWrite_number());
            novelRepository.memoSave(memoVO);
        }

        return "redirect:/novelnet?novel="+ memoVO.getM_num();
    }

    //이미지 업로드 로직. 배포전에 WebMvcConfig를 비롯해 경로수정할것.
    @PostMapping(value = "/novelnet/writeImg.do" ,  produces = "application/json")
    @ResponseBody
    public JsonObject uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {

        System.out.println("첨부파일명 "+multipartFile.getOriginalFilename());

        JsonObject jsonObject = new JsonObject();

        String fileRoot         = "C:\\code\\KimJeonghyun\\novelNet\\src\\main\\resources\\static\\noteImg\\";	  //저장될 외부 파일 경로
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


    //소설작성
    @GetMapping("/novelnet/regist")
    public String novelRegist(HttpSession session)
    {
        if (session.getAttribute("U_NUM")==null){
            return "redirect:/novelnet";
        }
        else
        {
            return "novel_regist";
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
        if (session.getAttribute("U_NUM") == null) { return "redirect:/novelnet/novel?n_num=" + n_num;  }


        int u_num = (Integer)session.getAttribute("U_NUM");

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

        System.out.println("첫태그 : "    + first_tag);
        System.out.println("태그리스트 : " + second_tag);
        System.out.println("유저번호 : "   + u_num);

        return "novel_update";
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
            System.out.println("작품명 : " + regist_title + "\n" +
                    "연재방식 : " + write_class + "\n" +
                    "독점여부 : " + monopoly + "\n" +
                    "연재상태 : " + consummation + "\n" +
                    "작품분류 : " + first + "\n" +
                    "해쉬태그 : " + hashtag_add + "\n" +
                    "작품소개 : " + novel_introduction);

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
        {   System.out.println("작품명 없음");
            return "redirect:/novelnet/regist";
        }

        //Hashtag 저장
        if(first.equals("none") || first == null) { hashtag_add = "기타," + hashtag_add.replace(" ", ""); }
        else{ hashtag_add = first.replace(" ", "") + "," + hashtag_add.replace(" ", ""); }

        if ((hashtag_add != null || !hashtag_add.equals("")) && novelVO.getN_num() != null)
        {
            novelRepository.TagSave(novelVO.getN_num(), hashtag_add);
        }

        return "redirect:/novelnet";
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
            System.out.println("작품명 : " + regist_title + "\n" +
                    "연재방식 : " + write_class + "\n" +
                    "독점여부 : " + monopoly + "\n" +
                    "연재상태 : " + consummation + "\n" +
                    "작품분류 : " + first + "\n" +
                    "해쉬태그 : " + hashtag_add + "\n" +
                    "작품소개 : " + novel_introduction + "\n" +
                    "작품번호 : " + n_num);

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
        {   System.out.println("작품명 없음");
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

        if (m_num == null || m_num =="")
        {
            m_num = "1";
        }
        if (n_num == null || n_num =="")
        {
            n_num = "1";
        }
        if (page == null || page =="")
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

        if (m_num == null)  { m_num = "1";}
        if (n_num == null)  { return "redirect:/novelnet"; }
        else
        {
            MemoVO memoVO = novelMapper.getMemo(n_num, m_num);
            if (memoVO.getM_num() == null)
            {
                return "redirect:/novelnet/novel?" +
                        "n_num="+n_num+
                        "&sort="+sort+
                        "&page="+page;
            }

            //다음화
            String next = novelMapper.getNextMemo(n_num, m_num);
            if (next != null)
            {
                next = "/novelnet/view?n_num="+n_num+
                                        "&sort="+sort+
                                        "&page="+page+
                                        "&chapter="+next;
                System.out.println(next);
            }

            //이전화
            String back = novelMapper.getBackMemo(n_num, m_num);
            if (back !=null)
            {
                back = "/novelnet/view?n_num="+n_num+
                                        "&sort="+sort+
                                        "&page="+page+
                                        "&chapter="+back;
                System.out.println(back);
            }
            System.out.println("소설번호" + n_num + " / 챕터번호" + m_num);

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

                //조회수증가
                if(session.getAttribute("chapter_"+m_num) == null){
                    session.setAttribute("chapter_"+m_num, m_num);
                    novelMapper.countUp(m_num);

                    System.out.println("조회수 증가");
                    System.out.println("챕터 세션 : "+ session.getAttribute("chapter_"+m_num));
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
        System.out.println(r_rnum);
        if(session.getAttribute("U_NUM") == null)
        {
            u_num = "0";
        }
        else
        {
            u_num = (String)session.getAttribute("U_NUM").toString(); //유저번호
        }
        System.out.println("유저번호 :" + u_num);
        System.out.println("메모 : "+ r_memo + "\n"+
                            "글번호 : " + m_num + "\n"+
                            "대댓글번호 : " + r_rnum + "\n"+
                            "소설번호 :" + n_num);
        if(r_memo != "" && r_memo != null){
            if (r_rnum == null || r_rnum == "0"){
                novelMapper.saveReply(u_num, n_num, m_num, r_memo);
                System.out.println("댓글 등록완료");
                state = "댓글 등록완료";
            }else
            {
                novelMapper.saveReReply(u_num, n_num, m_num, r_rnum, r_memo);
                System.out.println("대댓글 등록완료");
                state = "댓글 등록완료";
            }
        }
        return state;
    }

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
            System.out.println("신고여부"+cnt);
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

        System.out.println(r_num+"/"+type);

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
            System.out.println(updown+"/"+m_num);
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
            System.out.println(sort);
        }

        if(session.getAttribute("U_NUM") != null) {
            u_num = (String) session.getAttribute("U_NUM").toString(); //유저번호
        }

        System.out.println("유저번호 :" + u_num);

        if (n_num == null || n_num.matches("-?\\d+(\\.\\d+)?") == false ){ return "redirect:/novelnet"; } //소설데이터 검색 실패시
        else
        {
            //소설 데이터 검색
            System.out.println("책 번호 : " + n_num);
            NovelVO novelVO = new NovelVO();

            //존재여부 체크
            if(novelMapper.cheakBookDate(n_num) == 1)
            {
                try
                {
                    novelVO = novelMapper.getBookDate(n_num); //정보검색
                    System.out.println(novelVO);

                    int pageInt = Integer.parseInt(page); //페이지계산용

                    //페이징
                    PageingVO pageingVO = new PageingVO();
                    model.addAttribute("paging", novelRepository.novelPaging(n_num, pageInt));
                    System.out.println(pageingVO);

                    if(page != "1")
                    {
                        start = (pageInt-1) * 10;
                    }

                    //게시물 검색
                    List<MemoVO> memoVOList = novelMapper.getMemoDate(n_num,u_num,start,count,page,sort);

                    //북마크여부
                    int bookmark = searchMapper.cheakBookMark(u_num,n_num);

                    //작가검색
                    String writer = novelMapper.getWriter(novelVO.getU_num());
                    if (writer == null || writer == "")
                    {
                        System.out.println("잘못된 이용자입니다.");
                        writer = "가입번호 " + novelVO.getU_num() + " 작가님";
                        model.addAttribute("writer",writer);
                    }
                    else
                    {
                        System.out.println(writer);
                        model.addAttribute("writer",writer);
                    }

                    //태그검색
                    List<TagVO> tagVOList = novelMapper.getAllTag(n_num);
                    if(tagVOList != null)
                    {
                        System.out.println("태그" + tagVOList);
                        model.addAttribute("tag", tagVOList);
                    }

                    //마지막으로 본 게시물 검색
                    String lastChapter = novelMapper.getLastChapter(n_num,u_num);
                    System.out.println(lastChapter);
                    model.addAttribute("lastChpater", lastChapter);

                    //모델에 뷰로 담을 정보 전송
                    model.addAttribute("memoVOList", memoVOList);
                    model.addAttribute("novelVO", novelVO);
                    model.addAttribute("sort",sort);//정렬
                    model.addAttribute("nowPage",page);//페이지
                    model.addAttribute("bookmark",bookmark);

                    //작가의 다른 소설 검색
                    NovelVO subNovel = novelMapper.getAnotherBook(n_num, novelVO.getU_num());
                    //태그검색
                    List<TagVO> subTagList = novelMapper.getMiniTag(subNovel.getN_num());
                    if(subTagList != null)
                    {
                        model.addAttribute("subtag", subTagList);
                    }
                    model.addAttribute("subNovel", subNovel);

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

            System.out.println(id+"/" +n_num+ "/" +book_switch);

            if (book_switch.equals("0"))
            {
                int t = searchMapper.cheakBookMark(id, n_num);
                if(t==0)
                {
                    searchMapper.setBookMark(id, n_num);
                    bookmarkSwitch = "on";
                    System.out.println("북마크 되었습니다.");
                }
            }
            if (book_switch.equals("1"))
            {
                int bm_num = searchMapper.getBookMarkNum(id, n_num);
                searchMapper.deleteBookMark(bm_num);
                bookmarkSwitch = "off";
                System.out.println("북마크 취소되었습니다.");
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

            System.out.println(id+"/" +n_num+ "/" +n_num);

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

        String u_num;
        System.out.println("======================================");

        if(session.getAttribute("U_NUM") != null) {u_num = (String) session.getAttribute("U_NUM").toString(); } //유저번호
        else                                            {u_num = "20"; } //비로그인 테스트용


        //제대로 있는가 체크용. 만들고 나서 지울것
        List bookMakrList = searchMapper.findbookmark(u_num);
        System.out.println(bookMakrList);

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


        if (u_num != null){
            //북마크 갯수 확보
            int count = searchMapper.bookmarkCount(u_num, keyword, category);
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

            //검색(유저번호, 검색어, 업데이트 순서, 완결여부)
            List<NovelVO> novelList = searchMapper.getBookmarkList(u_num, keyword, newOld, category, start);
            System.out.println(novelList);
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

    @GetMapping("/t")
    public String t(){
        pageingService.setNowPage(15);
        pageingService.setTotalCount(154);

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
        return "test";
    }
}
