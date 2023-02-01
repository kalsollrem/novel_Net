package com.project.novelnet.repository;

import com.project.novelnet.Vo.NovelVO;
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
    List<TagVO> likeTagAndRcnt(@Param("u_num") String u_num);

    //프로필 주인의 소설
    List<NovelVO> getProfillNovelList(@Param("u_num") String u_num);

    //자기소개(게스트용)
    @Select("select u_myself,u_pic, u_nick from user where u_num = #{u_num}")
    ArrayList<UserVO> getMyself(@Param("u_num") String u_num);

    @Select("select u_num, u_nick , u_mail, u_pass, u_pic, u_myself from user where u_num = #{u_num}")
    ArrayList<UserVO> getProfill(@Param("u_num") String u_num);
}
