package com.project.novelnet.repository;

import com.project.novelnet.Vo.UserVO;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

public interface UserReopsitory {
    public int IdCheak(String id);

    public void login();

}
