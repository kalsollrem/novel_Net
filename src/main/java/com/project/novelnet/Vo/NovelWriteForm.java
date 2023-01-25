package com.project.novelnet.Vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NovelWriteForm {
    private String write_title;     //소설명
    private String write_memo;      //내용
    private String write_type;      //공지냐 연재냐
    private String write_number;    //소설번호

    private String Write_chapter;   //챕터번호
    private String sort;            //정렬
    private String page;            //페이지
}
