package com.project.novelnet.Dao;

import com.project.novelnet.Vo.MemoVO;
import com.project.novelnet.Vo.PageingVO;
import com.project.novelnet.Vo.UserVO;
import com.project.novelnet.repository.NovelMapper;
import com.project.novelnet.repository.NovelRepository;
import com.project.novelnet.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NovelDB implements NovelRepository {
    private final JdbcTemplate jdbcTemplate;

    public NovelDB(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private PreparedStatement pstmt;

    @Autowired
    private MailService mailService;

    @Autowired
    private NovelMapper novelMapper;

    @Override
    public MemoVO memoSave(MemoVO memoVO){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                pstmt = con.prepareStatement(
                        "INSERT INTO memo(m_title, m_memo, m_type, n_num) VALUES (?,?,?,?)",
                        new String[]{"ID"});
                //인덱스 파라미터, 나중에 SQL고치고 소설 번호 추가하는 작업 할것.
                pstmt.setString(1, memoVO.getM_title());
                pstmt.setString(2, memoVO.getM_memo());
                pstmt.setString(3, memoVO.getM_type());
                pstmt.setString(4, memoVO.getN_num());

                //생성값 리턴
                return pstmt;
            }
        }, keyHolder);
        Number keyValue = keyHolder.getKey();
        memoVO.setM_num(keyValue.intValue());


        return memoVO;
    }


    //메일체크
    @Override
    public int mailCheak(String e_mail) {

        int cnt = jdbcTemplate.queryForObject(
                "select count(u_mail) from user where u_mail = ?", Integer.class, e_mail);

        return cnt;
    }


    //유저 등록
    @Override
    public UserVO userJoin(UserVO userVO) {
        //코드생성
        String code = mailService.codemaker();

        System.out.println("아이디"+userVO.getU_mail() + "/ 비번 : " + userVO.getU_pass()+ "/ 닉 : "+userVO.getU_nick());
        System.out.println("인증코드" + code);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                pstmt = con.prepareStatement(
                        "INSERT INTO user(u_mail, u_pass, u_nick, u_code) VALUES (?,?,?,?)",
                        new String[]{"ID"});
                //인덱스 파라미터, 나중에 SQL고치고 소설 번호 추가하는 작업 할것.
                pstmt.setString(1, userVO.getU_mail());
                pstmt.setString(2, userVO.getU_pass());
                pstmt.setString(3, userVO.getU_nick());
                pstmt.setString(4, code);
                //생성값 리턴
                return pstmt;
            }
        }, keyHolder);
        Number keyValue = keyHolder.getKey();
        userVO.setU_num(keyValue.intValue());

        return userVO;

    }

    //제대로 됐는지 검증
    @Override
    public String findCode(int U_num){
        String code = jdbcTemplate.queryForObject("select u_code from user where u_num = ?", String.class, U_num);
        System.out.println("코드는 : "+ code);

        return code;
    }

    //유저인증
    @Override
    public void UesrOK(String code) {

        String userNum = jdbcTemplate.queryForObject("select u_num from user where u_code = ?", String.class, code);
        System.out.println("아이디: "+userNum);

        if (userNum != null)
        {
            jdbcTemplate.update("update user set u_ok = '1', u_code = 'done' where u_num = ?", userNum);
            System.out.println("인증 변경을 실행하였습니다");
        }
        else
        {
            System.out.println("인증 변경에 실패하였습니다.");
        }
    }

    @Override
    public UserVO getlogin(String id, String pass) {
        UserVO userVO = null;
        String sql = "SELECT * FROM user where u_mail = ? and u_pass = ?";


        return userVO;
    }

    @Override
    public void TagSave(int n_num,String taglist) {
        List<String> hashtag = new ArrayList<String>();

        if (taglist == null || !taglist.equals(""))
        {
            taglist = taglist.replace("#","");
            String[] tagSplit = taglist.split(",");
            hashtag.add("#"+tagSplit[0]);
            novelMapper.saveTag(n_num, hashtag.get(0), "1st");

            for(int i = 1 ; i<tagSplit.length; i++)
            {
                hashtag.add("#"+tagSplit[i]);
                System.out.println("해쉬태그 "+i+"번째 : "+hashtag.get(i));

                //해쉬태그 저장
                novelMapper.saveTag(n_num, hashtag.get(i), "2st");
            }
        }

    }


    //페이징
    @Override
    public PageingVO novelPaging(String n_num, int page) {

        PageingVO pageingVO = new PageingVO();

        int allmemo = novelMapper.getPages(n_num); //전체게시물숫자
        int allpage = allmemo/10+1;                //전체페이지

        int allLength;      //전체 페이지 간격
        int nowLength;      //내가 있는 간격 ex) 15페이지면 제 2간격(10~20)사이에있음
        int pageLeft=0;     //왼쪽버튼    [<]
        int pageRight=0;    //오른족버튼   [>]
        int bookcase=5;     //나눌간격

        //전체 페이지 간격 구하기
        if(allpage%bookcase == 0 )  { allLength = (allpage-1)/bookcase +1 ; }
        else                        { allLength = allpage/bookcase + 1; }

        //현재 간격 구하기
        if(page % bookcase == 0) { nowLength = (page-1)/bookcase +1;}
        else                     { nowLength = page/bookcase +1;}

        //왼쪽 버튼 구하기
        if (nowLength > 1){  pageLeft = (nowLength-1) * bookcase; }  //왼쪽 버튼 내간격이 0이면 왼쪽없음  ex)현재 간격이 3이면 30페이지

        //오른쪽 버튼 구하기
        if (nowLength < allLength && allpage>bookcase ) { pageRight = (nowLength * bookcase)+1;} //내 간격이 전체 간격과 같으면 우측 버튼 없음 ex)현재간격 3, 전체간격 3

        System.out.println("검색된 게시물 숫자 : " + allmemo);
        System.out.println("검색된 페이지 숫자 : " + allpage);
        System.out.println("내 전체 페이지 간격 : " + allLength);
        System.out.println("내가 있는 간격 : " + nowLength);
        System.out.println("왼쪽 버튼 : " + pageLeft);
        System.out.println("오른쪽 버튼 : " + pageRight);

        pageingVO.setAllmemo(allmemo);
        pageingVO.setAllpage(allpage);
        pageingVO.setBookcase(bookcase);

        pageingVO.setAllLength(allLength);
        pageingVO.setNowLength(nowLength);
        pageingVO.setPageLeft(pageLeft);
        pageingVO.setPageRight(pageRight);

        return pageingVO;
    }


}
