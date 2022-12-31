package com.project.novelnet.service;

import org.springframework.stereotype.Service;

@Service
public class ManageService {
    public boolean isInteger(String strValue) {
        try {
            Integer.parseInt(strValue);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
