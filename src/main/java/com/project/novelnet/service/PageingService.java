package com.project.novelnet.service;

import com.project.novelnet.Vo.PageingVO;
import org.springframework.stereotype.Service;

@Service
public class PageingService
{
    //입력변수
    private int totalCount; //전체 게시물 숫자
    private int nowPage;    //지금 있는 페이지

    //계산변수
    private int allPage;   //전체 페이지
    private int nowCase;   //현재 간격
    private int allCase;   //전채 간격
    private int leftPage;   //좌 버튼
    private int rightPage;   //우 버튼
    private int displayPage; //하단에 페이지를 몇개 표시할 것인가.


    //나누기용 변수
    private int showMemo;    //게시물을 몇페이지 단위로 나눌지
    private int dividPage;   //책장을 몇페이지 단위로 나눌지


    //나누기용 변수 셋팅
    public void setDivedDate()
    {
        showMemo  = 10;
        dividPage = 5;
    }

    public void setTotalCount(int totalCount)
    {
        this.totalCount = totalCount;
        this.nowPage    = nowPage;
        setDivedDate();
        setPageDate();
    }


    public void setPageDate(){
        //전체 페이지 갯수
        allPage = totalCount/showMemo;
        if(totalCount%showMemo != 0)
        {
            allPage = allPage+1;
        }

        //전채 책장 간격
        allCase = allPage/dividPage;
        if(totalCount%showMemo != 0)
        {
            allCase = allCase+1;
        }

        //현재 간격
        nowCase = nowPage/dividPage;
        if(nowPage%dividPage !=0){
            nowCase = nowCase+1;
        }

        //좌페이지
        if (nowCase != 0) {
            leftPage = (nowCase - 1) * dividPage;
        }

        //우페이지
        if(allCase > nowCase)
        {
            rightPage = (nowCase*dividPage)+1;
        }else
        {
            rightPage = 0;
        }

        //마지막 페이지 용
        if(totalCount%showMemo == 0) {
            displayPage = totalCount/10;
        }else
        {
            displayPage = totalCount/10+1 ;
        }
    }

    //Getter
    public int getAllPage()     {return allPage;}
    public int getNowCase()     {return nowCase;}
    public int getAllCase()     {return allCase;}
    public int getLeftPage()    {return leftPage;}
    public int getRightPage()   {return rightPage;}
    public int getDisplayPage() {return displayPage;}
    public void setNowPage(String nowPage){ this.nowPage = Integer.parseInt(nowPage); }
    public void setNowPage(int nowPage)   { this.nowPage = nowPage; }
}
