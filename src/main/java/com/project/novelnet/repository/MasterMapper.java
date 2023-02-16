package com.project.novelnet.repository;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public interface MasterMapper {
    //신고된 소설 리스트 보기
    public List<Map<String, Object>> novelShingo(@Param("searchType")String searchType, @Param("keyword")String keyword ,@Param("start")int start);

    //소설당 신고 갯수
    public int novelShingoCnt(@Param("searchType")String searchType, @Param("keyword")String keyword);

    //유저 레벨 변환
    @Update("update user set u_level=#{level} where u_num=#{u_num}")
    public int UserLevelChanger(@Param("level")int level, @Param("u_num")String u_num);


}
