package com.project.novelnet.repository;

import com.project.novelnet.Vo.NovelVO;
import com.project.novelnet.Vo.ReplyVO;
import com.project.novelnet.Vo.TagVO;
import com.project.novelnet.Vo.UserVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ProfillMapper
{
    //선호 장르+총댓글
    TagVO likeTagAndRcnt(@Param("u_num") String u_num);

    //프로필 주인의 소설
    List<NovelVO> getProfillNovelList(@Param("u_num") String u_num);

    //프로필 존재확인
    @Select("select count(u_num) from user where u_num = #{u_num}")
    public int findProfillOK(@Param("u_num") String u_num);

    //자기소개(게스트용)
    @Select("select u_myself,u_pic,u_nick, date_format(u_regdate, '%Y년 %m월 %d일')as u_regdate from user where u_num = #{u_num}")
    UserVO getMyself(@Param("u_num") String u_num);

    @Select("select u_num, u_nick , u_mail, u_pass, u_pic, u_myself, date_format(u_regdate, '%Y년 %m월 %d일')as u_regdate from user where u_num = #{u_num}")
    UserVO getProfill(@Param("u_num") String u_num);

    //리플가져오기
    ArrayList<ReplyVO> getMyAllReply(@Param("u_num") String u_num);
}
