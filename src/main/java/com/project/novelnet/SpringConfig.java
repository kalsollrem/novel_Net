package com.project.novelnet;

import com.project.novelnet.Dao.NovelDB;
import com.project.novelnet.Vo.MemoVO;
import com.project.novelnet.repository.JdbcMemberRepository;
import com.project.novelnet.repository.MemberRepositroy;
import com.project.novelnet.repository.NovelRepository;
import com.project.novelnet.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

//자바 코드로 스프링 빈을 등록하는 방법.
//MemoryMemberRepository는 자체 테스트용인데, DB에 연결하는 컨테이너로 변경할때 편리.

@Configuration
public class SpringConfig
{
    private final DataSource dataSource;

    public SpringConfig(DataSource dataSource){
        this.dataSource =dataSource;
    }

    @Bean
    public MemberRepositroy memberRepositroy(){

//        return new JdbcMemberRepository(dataSource);
        return new JdbcMemberRepository(dataSource);
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepositroy());
    }

    //노벨넷 관리
    @Bean
    public NovelRepository novelRepository(){return new NovelDB((dataSource));
    }

}
