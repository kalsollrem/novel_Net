package com.project.novelnet.repository;

import com.project.novelnet.Vo.NovelVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

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
    public void deleteBookMark(@Param("bm_num")int bm_num);

    //베스트 추천 findtype : count(조회수), best(추천수), bookmark(북마크수)
    List<NovelVO> findBest(@Param("findtype")String findtype, @Param("limit")int limit);

    //PD픽 리스트 검색
    @Select("SELECT pd_pick FROM MASTER")
    public String PdPickList();

    List<NovelVO> findPdPick(List<String> pdlist);

    //독점작 검색
    List<NovelVO> findonlyNovel();
    //내 북마크 검색
    @Select("select n_num from bookmark where u_num=#{u_num}")
    List<String> findbookmark(String u_num);

    //내 북마크 갯수 검색
    public int bookmarkCount(@Param("u_num")String u_num, @Param("keyword")String keyword, @Param("fin")String fin);

    //내 북마크 책장 데이터 검색(총 조회수, 총 추천수, 총 회차수, 다음화 포함)
    List<NovelVO> getBookmarkList(@Param("u_num")String u_num, @Param("keyword")String keyword, @Param("newOld")String newOld, @Param("fin")String fin, @Param("start")int start);

    //sort : 정렬기준(공개일, 조회수, 추천), mainTag : 검색태그, searchTag : 서치태그(태그추가시)
    //searchType: 검색할타입(제목,태그,작가), searchKeyword: 검색어
    //start : 페이징용
    List<NovelVO> getSearchNovelList(@Param("sort")String sort,
                                     @Param("mainTag")String mainTag,
                                     @Param("searchTag")String searchTag,
                                     @Param("searchType")String searchType,
                                     @Param("searchKeyword")String searchKeyword,
                                     @Param("start")int start);

    //검색된 갯수 확보
    public int searchNovelCount(@Param("mainTag")String mainTag,
                                @Param("searchTag")String searchTag,
                                @Param("searchType")String searchType,
                                @Param("searchKeyword")String searchKeyword);

    //검색 플러스 페이지용.
    List<NovelVO> getSearchPlusNovelList(@Param("sort")String sort,
                                         @Param("mainTag")String mainTag,
                                         @Param("searchTag")String searchTag,
                                         @Param("novelType")String novelType,
                                         @Param("doType")String doType,
                                         @Param("monopoly")String monopoly,
                                         @Param("start")int start);

    //검색 플러스 검색갯수
    public int searchPlusNovelCount(@Param("mainTag")String mainTag,
                                    @Param("searchTag")String searchTag,
                                    @Param("novelType")String novelType,
                                    @Param("doType")String doType,
                                    @Param("monopoly")String monopoly);

    //베스트 픽 검색
    List<NovelVO> bestNovelFinder(@Param("sort")String sort,@Param("carte")String carte, @Param("start")int start);

    //숫자확인
    public int bestNovelCount(@Param("sort")String sort,@Param("carte")String carte, @Param("start")int start);
}
