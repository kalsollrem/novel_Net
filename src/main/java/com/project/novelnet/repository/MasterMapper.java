package com.project.novelnet.repository;

import com.project.novelnet.Vo.MasterVO.MasterNovel;
import com.project.novelnet.Vo.MasterVO.MasterReply;
import com.project.novelnet.Vo.NovelVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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


    //유저 레벨 변환
    @Update("update user set u_level=#{level} where u_num=#{u_num}")
    public int UserLevelChanger(@Param("level")int level, @Param("u_num")String u_num);

    //유저 레벨 변환
    @Delete("delete from m_warning where mw_num = #{mw_num}")
    public int warningDel(@Param("mw_num")int mw_num);



    //======================================================================

    public int novelCnt(@Param("searchType")String searchType, @Param("keyword")String keyword , @Param("sort")String sort);
    List<NovelVO> masterNovelList(@Param("searchType")String searchType, @Param("keyword")String keyword , @Param("sort")String sort, @Param("start")int start);

}
