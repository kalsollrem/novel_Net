package com.project.novelnet.repository;

import com.project.novelnet.Vo.NovelVO;
import com.project.novelnet.Vo.TagVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfillMapper
{
    //선호 장르+총댓글
    List<TagVO> likeTagAndRcnt(@Param("u_num") String u_num);
}
