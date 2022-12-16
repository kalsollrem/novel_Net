package com.project.novelnet.service;

import com.project.novelnet.domain.Member;
import com.project.novelnet.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


class MemberServiceTest {


    //방법1(new) : MemberService에서 new로 인스턴스를 생성했음으로 실질 2개의 중복인스턴스가 생김으로 문제가 생길 수 있음.
    //MemberService memberService = new MemberService();
    //MemoryMemberRepository memberRepository = new MemoryMemberRepository();

    //방법2(호출변경):MemberService에서 MemberRepositroy를 호출할 수 있게 변경. 이런 것을 디펜던시 인젝션이라함.
    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach     //시행전 동작
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach      //시행후 동작
    public void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void join_회원가입() {
        //given
        Member member = new Member();
        member.setName("spring");

        //when
        Long saveId = memberService.join(member);

        //then
        Member finderMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(finderMember.getName());
    }

    @Test
    void findMembers_중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원");

//      문법1 : try캐치로 중복메세지확인
//        try {
//            memberService.join(member2);
//            fail("");
//        }catch (IllegalStateException e)
//        {
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원");
//            //성공
//        }
        //when
    }

    @Test
    void findOne_회원한명찾기() {
    }
}