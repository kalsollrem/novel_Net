package com.project.novelnet.repository;

import com.project.novelnet.Vo.MemoVO;
import com.project.novelnet.Vo.PageingVO;
import com.project.novelnet.Vo.UserVO;

import java.util.List;
import java.util.Optional;

public interface NovelRepository {
    //글쓰기
    MemoVO memoSave(MemoVO memoVO);

    public int mailCheak(String e_mail);

    UserVO userJoin(UserVO userVO);

    public String findCode(int U_num);

    public void UesrOK(String code);

    UserVO getlogin(String id, String pass);

    public void TagSave(int n_num, String taglist);

    PageingVO novelPaging(String n_num, int page);

}
