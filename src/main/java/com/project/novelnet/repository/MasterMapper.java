package com.project.novelnet.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public interface MasterMapper {
    public List<Map<String, Object>> novelShingo(@Param("searchType")String searchType, @Param("keyword")String keyword ,@Param("start")int start);
    public int novelShingoCnt(@Param("searchType")String searchType, @Param("keyword")String keyword);
}
