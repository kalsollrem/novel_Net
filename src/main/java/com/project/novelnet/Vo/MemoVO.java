package com.project.novelnet.Vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
public class MemoVO {
    private Integer m_num;
    private String n_num;
    private String m_title;
    private String m_memo;
    private String m_type;
    private String m_date;
    private String m_count; //Integer에서 변경
    private Integer m_good;
    private Integer b_stop;


    private Integer r_cnt;  //댓글갯수
    private Integer ep;     //화
    private String  novel_name; //소설이름
    private String  u_num; //작성자 번호
    private Integer cheak;
}
