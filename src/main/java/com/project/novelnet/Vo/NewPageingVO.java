package com.project.novelnet.Vo;

import lombok.Data;

@Data
public class NewPageingVO {
    private int allPage;
    private int nowCase;
    private int allCase;
    private int leftPage;
    private int rightPage;
    private int displayPage;
}
