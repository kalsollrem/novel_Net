package com.project.novelnet.repository;

import com.project.novelnet.Vo.MasterVO.MasterNovel;
import com.project.novelnet.Vo.MasterVO.MasterReply;
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

    //------------------------------------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------------------------------------


    //유저 레벨 변환
    @Update("update user set u_level=#{level} where u_num=#{u_num}")
    public int UserLevelChanger(@Param("level")int level, @Param("u_num")String u_num);

    //유저 레벨 변환
    @Delete("delete from m_warning where mw_num = #{mw_num}")
    public int warningDel(@Param("mw_num")int mw_num);


}
