package com.project.novelnet.Vo;

import lombok.Data;

@Data
public class PageingVO {
    int allmemo;        //전체 게시물 숫자
    int allpage;        //전체 서고 숫자
    int bookcase;       //간격(5개)

    int allLength;      //전체 페이지 간격
    int nowLength;      //내가 있는 간격 ex) 15페이지면 제 2간격(10~20)사이에있음
    int pageLeft;       //왼쪽버튼    [<]
    int pageRight;      //오른족버튼   [>]
}
