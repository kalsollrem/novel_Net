package com.project.novelnet.Vo;

import lombok.Data;

@Data
public class UserVO {
    private Integer u_num;    //Integer에서 String로 바꿈.
    private String u_mail;    //메일주소
    private String u_pass;    //비번
    private String u_nick;    //닉네임
    private String u_pic;     //사진
    private String u_ok;      //메일인증확인
    private Integer u_level;  //인증레벨
    private String u_age;     //유저나이(사용안함)
    private String u_like;    //선호(사용안함)
    private String u_myself;  //자기소개
    private String u_regdate; //가입일
    private String u_code;    //이메일코드

    private int goRcnt;   //내가 댓글 신고한 횟수
    private int reRcnt;   //내가 댓글 신고당한 횟수
    private int goNcnt;   //내가 소설 신고한 횟수
    private int reNcnt;   //내가 소설 신고당한 횟수

}
