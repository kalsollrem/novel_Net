package com.project.novelnet.controller;
import com.project.novelnet.repository.MasterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Controller
public class MasterController {
    @Autowired
    private MasterMapper masterMapper;

    @GetMapping("/master/index")
    public String masterIndex(Model model){
        List<Map<String, Object>> list = masterMapper.novelShingo();
        System.out.println(list);

        model.addAttribute("list",list);
        return "master_Page";
    }
}
