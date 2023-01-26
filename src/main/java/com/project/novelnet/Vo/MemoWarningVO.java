package com.project.novelnet.Vo;

import lombok.Data;

@Data
public class MemoWarningVO {
    private Integer mw_num;    //신고 번호
    private Integer n_num;     //신고 소설
    private Integer m_num;     //신고 당한 게시물
    private Integer u_num;     //신고자
    private String  w_why;     //이유

    //편의용
    private String  timeDiff;  //시간차이
}
