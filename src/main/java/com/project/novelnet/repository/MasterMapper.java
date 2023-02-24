package com.project.novelnet.repository;

import com.project.novelnet.Vo.MasterVO.MasterMemoVO;
import com.project.novelnet.Vo.MasterVO.MasterNovel;
import com.project.novelnet.Vo.MasterVO.MasterReply;
import com.project.novelnet.Vo.NovelVO;
import com.project.novelnet.Vo.PdPickVO;
import com.project.novelnet.Vo.UserVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public interface MasterMapper {
    //신고된 소설 리스트 보기
    //List<Map<String, Object>>에서 VO로 변경
    List<MasterNovel> novelShingo(@Param("searchType")String searchType, @Param("keyword")String keyword , @Param("start")int start);

    //소설당 신고 갯수
    public int novelShingoCnt(@Param("searchType")String searchType, @Param("keyword")String keyword);

    //------------------------------------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------------------------------------

    //신고된 댓글 리스트 보기
    List<MasterReply> replyShingo(@Param("searchType")String searchType, @Param("keyword")String keyword , @Param("start")int start);

    //소설당 댓글 갯수
    public int replyShingoCnt(@Param("searchType")String searchType, @Param("keyword")String keyword);

    //댓글 블라인드 처리
    @Update("update reply set r_state='blind' where r_num=#{r_num}")
    public int replyBlind(@Param("r_num")String r_num);

    //------------------------------------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------------------------------------

    //유저 데이터 가져오기
    List<UserVO> masterUserList(@Param("searchType")String searchType, @Param("keyword")String keyword ,@Param("sort")String sort, @Param("start")int start);

    //유저 인원수 파악
    public int userCnt(@Param("searchType")String searchType, @Param("keyword")String keyword ,@Param("sort")String sort);

    //유저 레벨 변환
    @Update("update user set u_level=#{level} where u_num=#{u_num}")
    public int UserLevelChanger(@Param("level")int level, @Param("u_num")String u_num);

    //유저 레벨 변환
    @Delete("delete from m_warning where mw_num = #{mw_num}")
    public int warningDel(@Param("mw_num")int mw_num);

    //유저삭제
    @Delete("delete from user where u_num = #{u_num}")
    public int deleteUser(@Param("u_num")int u_num);


    //======================================================================

    //마스터 페이지용 소설 갯수 새기
    public int novelCnt(@Param("searchType")String searchType, @Param("keyword")String keyword , @Param("sort")String sort);

    //마스터 소설 리스트
    List<NovelVO> masterNovelList(@Param("searchType")String searchType, @Param("keyword")String keyword , @Param("sort")String sort, @Param("start")int start);

    //소설 정지&정지해제
    @Update("update novel set stopPoint = #{stopPoint} where n_num=#{n_num}")
    public int masterNovelSwitch(@Param("n_num") int n_num, @Param("stopPoint")int stopPoint);

    //pd픽 가져오기
    List<PdPickVO> pdPickList();

    //pd픽 선정
    @Insert("INSERT INTO pd_pick (n_num) VALUES (#{n_num})")
    public int pdPickChoice(@Param("n_num")int n_num);

    //pd픽 해제
    @Delete("delete from pd_pick where n_num = #{n_num}")
    public int pdPickDelete(@Param("n_num")int n_num);

    //==============================================================
    //공지작성
    public int writeGongji(MasterMemoVO memoVO);

    //공지수정
    public int reWriteGongJi(MasterMemoVO memoVO);

    //공지 커버찾기
    @Select("select ma_cover from mastermemo where ma_num = #{ma_num}")
    public String findCover(@Param("ma_num")int ma_num);

    //공지 정보 찾기
    @Select("select ma_num, u_num, ma_title, ma_memo, ma_cover, ma_date, ma_type from mastermemo where ma_num=#{ma_num}")
    public MasterMemoVO findGonjiDate(@Param("ma_num")int ma_num);

}
