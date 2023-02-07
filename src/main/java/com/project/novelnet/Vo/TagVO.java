package com.project.novelnet.Vo;

import lombok.Data;

@Data
public class TagVO {
    String n_num;
    String h_tag;
    String c_carte;
    String t_num;

    //편의용
    String h_max; //최대 갯수의 태그
    String r_max; //전체리플갯수
}
