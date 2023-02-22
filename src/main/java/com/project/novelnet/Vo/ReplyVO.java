package com.project.novelnet.Vo;

import lombok.Data;

@Data
public class ReplyVO {
    private Integer r_num;  //댓글번호
    private String u_num;   //작성자 번호
    private String r_rnum;  //대댓글 번호
    private String r_memo;  //댓글내용
    private Integer r_baby; //대댓글 갯수
    private String r_state; //블라인드 처리 여부

    private String m_num;   //작성 게시물
    private String n_num;   //작성 소설
    private String r_date;  //작성시간

    private Integer r_good; //추천
    private Integer r_bad;  //비추

    //편의용
    private String nick;        //닉네임
    private String pic;         //프로필
    private String n_title;     //소설제목
    private String m_title;     //챕터제목
    private String stopCnt;     //챕터제목
    private String rRcnt;         //대댓글갯수
}
