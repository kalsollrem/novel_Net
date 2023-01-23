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


}
