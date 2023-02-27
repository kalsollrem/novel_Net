package com.project.novelnet.repository;

import com.project.novelnet.Vo.MemoVO;
import com.project.novelnet.Vo.UserVO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO member(name) values(#{id})")
    public void insert(@Param("id")String id);

    @Select("select u_pass from user where u_num = #{num}")
    public String getid(@Param("num")String num);

    //Vo로 검색하는법
    @Select("select * from user where u_num = #{userVO.u_num}")
    UserVO getFindPass(@Param("userVO")UserVO userVO);

    //유저 데이터 가져오기
    @Select("select * from user where u_mail = #{id} and u_pass = #{pass}")
    UserVO getUserData(@Param("id")String id, @Param("pass")String pass);

    //해쉬맵 테스트
    @Select("select * from user where u_mail = #{id} and u_pass = #{pass}")
    UserVO getUserDataHash(@Param("id")String id, @Param("pass")String pass);

    //잃어버린 비밀번호 가져오기
    @Select("select u_pass from user where u_mail = #{u_mail}")
    public String getLostId(@Param("u_mail")String u_mail);

    //유저인증
    @Select("select u_num from user where u_code = #{u_code}")
    public String UesrJoinFind(@Param("u_code")String u_code);

    //유저변경
    @Update("update user set u_ok = '1', u_code = 'done' where u_num = #{u_num}")
    public int UesrJoinEnd(@Param("u_num")String u_num);

    //유저 이미지 탐색
    @Select("select u_pic from user where u_num = #{u_num}")
    public String findUserCover(@Param("u_num")String u_num);

    //유저 이미지 변경
    @Update("update user set u_pic=#{u_pic} where u_num=#{u_num}")
    public int changeProfill(@Param("u_num")String u_num,@Param("u_pic")String u_pic);

    //유저 프로필 변경
    @Update("update user set u_nick=#{nick},u_pass=#{pass},u_myself=#{intro} where u_num=#{u_num}")
    public int updateUserDate(@Param("u_num")String u_num,
                              @Param("nick")String nick,
                              @Param("pass")String pass,
                              @Param("intro")String intro);

}
