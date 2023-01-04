package com.project.novelnet.service;

import org.springframework.stereotype.Service;

@Service
public class ManageService {
    //정수 확인 클래스
    public boolean isInteger(String strValue) {
        try {
            Integer.parseInt(strValue);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
