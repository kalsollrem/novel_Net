package com.project.novelnet.Vo;

import lombok.Data;

@Data
public class ReplyVO {
    Integer r_num;  //댓글번호
    String u_num;   //작성자 번호
    String r_rnum;  //데댓글 번호
    String r_memo;  //댓글내용
    Integer r_baby; //대댓글 갯수

    String m_num;   //작성 게시물
    String n_num;   //작성 소설
    String r_date;  //작성시간

    Integer r_good; //추천
    Integer r_bad;  //비추

    //편의용
    String nick;    //닉네임
    String pic;     //프로필
}
