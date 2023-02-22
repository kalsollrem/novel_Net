package com.project.novelnet.Vo;

import lombok.Data;

@Data
public class PageingVO {
    private int allmemo;        //전체 게시물 숫자
    private int allpage;        //전체 서고 숫자
    private int bookcase;       //간격(5개)

    private int allLength;      //전체 페이지 간격
    private int nowLength;      //내가 있는 간격 ex) 15페이지면 제 2간격(10~20)사이에있음
    private int pageLeft;       //왼쪽버튼    [<]
    private int pageRight;      //오른족버튼   [>]
}
