package com.project.novelnet.repository;

import com.project.novelnet.Vo.MemoWarningVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarningMapper
{
    //게시물 경고 삭제
    @Delete("delete  from m_warning where n_num = #{n_num} and u_num = #{u_num}")
    public void deleteMemoWarning(@Param("n_num")int n_num, @Param("u_num")int u_num);

    //모든 게시물 경고 검색
    @Select("select * from m_warning order by n_num desc, m_num asc")
    List<MemoWarningVO> findAllMemoWarning();

    //게시물 경고 등록
    @Insert("insert into m_warning(u_num, w_why, n_num, m_num) values(#{u_num}, #{w_why}, #{n_num}, #{m_num})")
    public void setMemoWarning(@Param("n_num")int n_num, @Param("u_num")int u_num,
                               @Param("m_num")int m_num, @Param("w_why")String w_why);

    //게시물 경고 기록 여부(n_num과 u_num이 없거나, timeDiff가 1440 이상이면 가능)
    @Select("select TIMESTAMPDIFF(minute, mw_date, SYSDATE())as timeDiff " +
            "from m_warning where n_num =#{n_num} and u_num =#{u_num} order by mw_num desc limit 1;")
    public String cheakMemoWarning(@Param("n_num")int n_num,@Param("u_num")int u_num);

}
