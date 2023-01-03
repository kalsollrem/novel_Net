package com.project.novelnet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchController
{
    @GetMapping("novelnet/search")
    public String searchPage(){

        return "search";
    }
}
