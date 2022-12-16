package com.project.novelnet.service;

import com.project.novelnet.Vo.MemoVO;
import com.project.novelnet.Vo.UserVO;
import com.project.novelnet.repository.UserMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class MapperService implements UserMapper {

    @Autowired
    private UserVO userVO;

    @Autowired
    private MemoVO memoVO;

    private List<MemoVO> memoVOList;

    @Override
    public void insert(String id) {

    }

    @Override
    public String getid(String num) {
        return num;
    }

    @Override
    public UserVO getFindPass(UserVO userVO) {
        return userVO;
    }

    @Override
    public UserVO getUserData(String id, String pass) {

        return userVO;
    }

    @Override
    public UserVO getUserDataHash(String id, String pass) {
        return null;
    }


}
