package com.project.novelnet.Vo;

import lombok.Data;

@Data
public class UserVO {
    private Integer u_num;   //Integer에서 String로 바꿈.
    private String u_mail;
    private String u_pass;
    private String u_nick;
    private String u_ok;
    private Integer u_level;
    private String u_age;
    private String u_like;
    private String u_myself;
    private String u_code;

}
