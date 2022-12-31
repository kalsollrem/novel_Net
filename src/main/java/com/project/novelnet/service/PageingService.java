package com.project.novelnet.service;

import com.project.novelnet.Vo.PageingVO;

public class PageingService
{
    private int totalCount; //전체 게시물 숫자
    private int nowPage;    //지금 있는 페이지

    private int endPage;    //끝 페이지
    private int rightPage;   //우 버튼
    private int leftPage;   //좌 버튼

    private int displayPage;//하단의 페이지 숫자

    //나누는 용도
    private int showMemo;   //보여줄 게시물 숫자
    private int dividPage;   //보여줄 페이지 숫자

    public void setTotalCount(int totalCount, int nowPage)
    {
        this.totalCount = totalCount;
        this.nowPage    = nowPage;
        setDivedDate();
        setPageDate();
    }

    public void setPageDate(){
        //전체 페이지 갯수
        int allpage = totalCount/showMemo;
        if(totalCount%showMemo != 0)
        {
            allpage = allpage+1;
        }

        //좌페이지
        if(nowPage/dividPage != 0)
        {
            leftPage = (nowPage/dividPage) -1;
        }

        //우페이지
        if((allpage/dividPage+1)!=(nowPage/dividPage+1))
        {
            rightPage = ((nowPage/dividPage+1)*10+1);
        }



    }

    //보여줄 게시물 숫자 셋팅
    public void setDivedDate()
    {
        showMemo  = 10;
        dividPage = 10;
    }
}
