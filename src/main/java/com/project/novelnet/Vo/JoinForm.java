package com.project.novelnet.Vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinForm {
    private String e_mail;          //이메일
    private String password;        //비밀번호
    private String password_cheak;  //비밀번호 체크
    private String nick_name;       //닉네임
    private String sign_contract;   //약관동의
    private String age_over;        //연령
}
