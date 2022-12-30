package com.project.novelnet.repository;

import com.project.novelnet.Vo.NovelVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface SearchMapper
{
    List<NovelVO> findMyBookList(String u_num);

    @Insert("insert into bookmark(u_num, n_num) values(#{u_num}, #{n_num})")
    public void setBookMark(String u_num, String n_num);

    //북마크 여부 체크
    @Select("select count(n_num) as C from bookmark where n_num=#{n_num} and u_num=#{u_num}")
    public int cheakBookMark(String u_num, String n_num);

    //븍마크 번호 가져오기
    @Select("select bm_num from bookmark where n_num=#{n_num} and u_num=#{u_num}")
    public int getBookMarkNum(String u_num, String n_num);

    //북마크 제거
    @Delete("DELETE FROM bookmark WHERE bm_num = #{bm_num}")
    public void deleteBookMark(int bm_num);

    //베스트 추천 findtype : count(조회수), best(추천수), bookmark(북마크수)
    List<NovelVO> findBest(@Param("findtype")String findtype, @Param("limit")int limit);

    //PD픽 리스트 검색
    @Select("SELECT pd_pick FROM MASTER")
    public String PdPickList();

    List<NovelVO> findPdPick(List<String> pdlist);

    //내 북마크 검색
    @Select("select n_num from bookmark where u_num=#{u_num}")
    List<String> findbookmark(String u_num);

    //내 북마크 검색
    @Select("select count(n_num) as count from bookmark where u_num=#{u_num}")
    String bookmarkCount(String u_num);

    //내 북마크 책장 데이터 검색(총 조회수, 총 추천수, 총 회차수, 다음화 포함)
    List<NovelVO> getBookmarkList(@Param("u_num")String u_num, @Param("keyword")String keyword, @Param("newOld")String newOld, @Param("fin")String fin);

}
