package com.project.novelnet.service;

import org.springframework.stereotype.Service;

@Service
public class ManageService {
    //정수 확인 클래스
    public boolean isInteger(String strValue) {
        try {
            if(Integer.parseInt(strValue)>=0) {return true;}
            else                              {return false;}
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
