package com.project.novelnet;

import com.project.novelnet.Vo.MemoVO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest
class NovelVONetApplicationTests {

    @Autowired
    private DataSource ds;

    @Test
    void contextLoads() {
    }

}
