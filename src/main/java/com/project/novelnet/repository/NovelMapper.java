package com.project.novelnet.repository;

import com.project.novelnet.Vo.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NovelMapper {

    @Results(id="Memo", value={
        @Result(property="m_num", column="m_num"),
        @Result(property="n_num", column="n_num"),
        @Result(property="m_title", column="m_title"),
        @Result(property="m_memo", column="m_memo"),
        @Result(property="m_date", column="m_date"),
        @Result(property="m_count", column="m_count"),
        @Result(property="m_good", column="m_good"),
        @Result(property="b_stop", column="b_stop")
    })

    //소설 게시물 보기
    @Select("select m_num, m_title, m_memo, m_date, m_count, m_good, b_stop, m_type, " +
            "(select n_title from novel where n_num=${n_num})as novel_name " +
            "from memo where n_num = ${n_num} and m_num=#{m_num}")
    public MemoVO getMemo(@Param("n_num")String n_num, @Param("m_num")String m_num);

    //다음 게시물
    @Select("select m_num from memo where m_num > #{m_num} and n_num = #{n_num} and m_type='ep' limit 1;")
    public String getNextMemo(@Param("n_num")String n_num, @Param("m_num")String m_num);

    //이전게시물
    @Select("select m_num from memo where m_num < #{m_num} and n_num = #{n_num} and m_type='ep' order by m_num desc limit 1;")
    public String getBackMemo(@Param("n_num")String n_num, @Param("m_num")String m_num);

    //마지막 게시물
    @Select("select m_num from m_record where n_num=#{n_num} and u_num=#{u_num} order by mr_num desc limit 1")
    public String getLastChapter(@Param("n_num")String n_num, @Param("u_num")String u_num);

    //수정권환 확인
    @Select("select count(B.u_num) as cnt from memo A left join novel B on A.n_num = B.n_num where m_num = #{m_num} and u_num=#{u_num}")
    public int UpdateOkCheaker(@Param("m_num")String m_num , @Param("u_num")String u_num);

    @Select("select * from memo")
    List<MemoVO> getAllMemo();

    //게시물 리스트 검색.
    @Select("select  @ep:=@ep+1 AS ep, m_type, m_num from (SELECT @ep:=0)as n, memo where n_num = #{n_num} and m_type='ep'")
    List<MemoVO> getEpMemo(@Param("n_num")String n_num);

    //태그등록
    @Insert("insert into hashtag(n_num, h_tag, t_carte) values (#{n_num}, #{tag}, #{type})")
    public void saveTag(int n_num, String tag, String type);

    //Novel Regist(소설등록)
    public void novelRegist(NovelVO novelVO);

    //Novel Update(소설수정)
    public void novelUpdate(NovelVO novelVO);

    //Cover Image Update(커버 업데이트)
    @Update("UPDATE novel SET n_cover = #{n_cover} WHERE n_num = #{n_num}")
    public void updateCover(@Param("n_cover")String n_cover, @Param("n_num")int n_num);

    //작가검색
    @Select("select u_nick from user where u_num = #{u_num}")
    public String getWriter(@Param("u_num") String u_num);

    //Novel Find(소설 정보 검색)
    public NovelVO getBookDate(String n_num);

    //Novel Date Cheak(소설 존재여부 확인)
    public int cheakBookDate(String n_num);

    //Novel Memo Find(소설 게시물 검색)
    List<MemoVO> getMemoDate(String n_num, String u_num, int start, int count, String page, String sort);

    //Writer another Novel Find(작가의 다른 소설 검색)
    public NovelVO getAnotherBook(@Param("n_num")String n_num, @Param("au_num")String au_num);

    //find tag List(태그리스트 검색)
    List<TagVO> getAllTag(String n_num);

    //find tag List limit 5(태그 5개 검색)
    List<TagVO> getMiniTag(int n_num);

    //태그 일렬 검색
    @Select("Select GROUP_CONCAT(h_tag SEPARATOR',')AS tags from hashtag where n_num=#{n_num} and t_carte=#{carte}")
    public String tags(@Param("n_num")String n_num, @Param("carte")String carte);

    //태그 제거
    @Delete("delete from hashtag where n_num = #{n_num}")
    public void deleteTags(@Param("n_num")String n_num);

    @Insert("insert into m_record(n_num, m_num, u_num) values(#{n_num},#{m_num},#{u_num})")
    public void setRecord(@Param("n_num")String r_num, @Param("m_num")String m_num, @Param("u_num")String u_num);

    //작성자 권환 확인
    @Select("select count(n_num) from novel where n_num = ${n_num} and u_num=#{u_num}")
    public int memoOK(int u_num, String n_num);

    //페이징
    //전체페이지 계산
    @Select("select count(m_num) from memo where n_num = #{n_num}")
    public int getPages(@Param("n_num") String n_num);

    //댓글작성
    @Insert("insert into reply(u_num, n_num, m_num, r_memo) " +
                    "values (#{u_num},#{n_num},#{m_num},#{r_memo})")
    public void saveReply(String u_num, String n_num, String m_num, String r_memo);

    //대댓글작성
    @Insert("insert into reply(u_num, n_num, m_num, r_rnum, r_memo) " +
                    "values (#{u_num},#{n_num},#{m_num},#{r_rnum},#{r_memo})")
    public void saveReReply(String u_num, String n_num, String m_num, String r_rnum, String r_memo);

    //댓글조회
    //ASC  = [ ORDER BY IF(r_rnum = 0, r_num, r_rnum)asc, r_num ]
    //DESC = [ ORDER BY IF(r_rnum = 0, r_num, r_rnum)desc, r_rnum!=0, r_num asc ]
    @Select("select A.r_num, A.u_num, A.r_rnum, A.r_memo, A.m_num, A.n_num, A.r_date, A.r_good, A.r_bad, A.r_baby, B.u_pic, B.u_nick as nick " +
            "from reply A " +
            "inner join user B " +
            "on A.u_num = B.u_num " +
            "where n_num = #{n_num} and m_num = #{m_num} " +
            "ORDER BY IF(r_rnum = 0, r_num, r_rnum)asc, r_num")
    List<ReplyVO> getReply(String m_num, String n_num);

    //댓글 갯수조회
    @Select("select count(r_num)as cnt from reply where n_num = #{n_num} and m_num = #{m_num} ")
    public int getReplyCount(String m_num, String n_num);

    //조회수증가
    @Update("UPDATE memo SET m_count = m_count+1 WHERE m_num = #{m_num}")
    public void countUp(String m_num);

    //추천
    @Update("UPDATE memo SET m_good = m_good+1 WHERE m_num=#{m_num}")
    public void goodUp(String m_num);

    //비추천
    @Update("UPDATE memo SET m_good = if(m_good>0, m_good-1, 0) WHERE m_num=#{m_num}")
    public void goodDown(String m_num);

    //추천숫자
    @Select("SELECT m_good FROM memo WHERE m_num=#{m_num}")
    public int getGoodCount(String m_num);

    //댓글추천
    @Update("update reply SET r_good = r_good+1 where r_num=#{r_num}")
    public void replyGoodUp(@Param("r_num")String r_num);

    //댓글비추
    @Update("update reply SET r_bad = r_bad+1 where r_num=#{r_num}")
    public void replyBadUp(@Param("r_num")String r_num);

    //신고자 확인
    @Select("select count(r_num)as cnt from r_warning where r_num=#{r_num} and u_num=#{u_num}")
    public int replyWarningCheak(@Param("r_num")String r_num, @Param("u_num")String u_num);

    //신고
    @Insert("insert into r_warning(r_num, u_num) values(#{r_num}, #{u_num})")
    public int replyWarning(@Param("r_num")String r_num, @Param("u_num")String u_num);
}
