package com.project.novelnet.Vo;

import lombok.Data;

@Data
public class TagVO {
    private String n_num;
    private String h_tag;
    private String c_carte;
    private String t_num;

    //편의용
    private String h_max; //최대 갯수의 태그
    private String r_max; //전체리플갯수
}
