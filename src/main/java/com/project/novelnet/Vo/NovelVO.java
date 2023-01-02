package com.project.novelnet.Vo;

import lombok.Data;

@Data
public class NovelVO {
    private Integer n_num;          //작품 넘버
    private String u_num;           //작성자 번호
    private String n_title;         //작품 제목
    private String n_introduction;  //작품 제목
    private String n_date;          //최신 게시물 작성일
    private String n_type;          //자유&습작 여부
    private String n_monopoly;      //독점 플랫폼 여부
    private String n_fin;           //완결여부
    private Integer n_stop;         //신고횟수

    private Integer n_good;         //추천
    private Integer n_count ;       //조회수

    private String n_cover;         //책 커버

    //편의용 변수
    private Integer n_chapters ;       //총화수
    private Integer f_chapter ;        //첫번째화
    private Integer last_chapter ;     //마지막으로 본 에피소드
    private Integer rnum ;             //마지막으로 본 에피소드의 번호
    private Integer nextNum ;          //다음글 m_num
    private String  nick;              //작성자 닉네임
    private String  all_tag;           //모든 태그
    private Integer noType ;           //union ALL 사용시 나눌때 사용
    private Integer ep_count;          //조회수 합산용
    private Integer newMemo;           //신규 등록 여부. 24미만일경우 NEW띄울때 사용.
}
