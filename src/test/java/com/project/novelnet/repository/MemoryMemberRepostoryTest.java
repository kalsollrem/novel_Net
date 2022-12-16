package com.project.novelnet.repository;

import com.project.novelnet.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;

public class MemoryMemberRepostoryTest {
    MemoryMemberRepository repositroy = new MemoryMemberRepository();

    @AfterEach  //메소드가 끝날때마다 메모리를 리셋. 사용을 위해서 MemoryMemberRepository에 clearStore클래스 작성
    public void afterEach()
    {
        repositroy.clearStore();
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("tr");

        repositroy.save(member);

        Member result = repositroy.findById(member.getId()).get();
        //Assertions.assertEquals( member, result);     //여부만 알려줌
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repositroy.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repositroy.save(member2);

        Member result = repositroy.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spirng1");
        repositroy.save(member1);

        Member member2 = new Member();
        member2.setName("spirng2");
        repositroy.save(member2);

        List<Member> result = repositroy.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
