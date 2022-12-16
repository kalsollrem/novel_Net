package com.project.novelnet.service;

import com.project.novelnet.domain.Member;
import com.project.novelnet.repository.MemberRepositroy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public class MemberService {
    private final MemberRepositroy memberRepositroy;

    public MemberService(MemberRepositroy memberRepositroy) {
        this.memberRepositroy = memberRepositroy;
    }

    //회원가입
    public Long join(Member member){
        //중복명칭 확인
        valDupMember(member);
        memberRepositroy.save(member); //멤버저장
        return member.getId();
    }

    private void valDupMember(Member member) {
        memberRepositroy.findByName(member.getName())            //Optional이 있으면 null이 있어도 가능
            .ifPresent(m ->{
                throw  new IllegalStateException("이미 존재하는 회원");
            });
    }


    //전체 회원조회
    public List<Member> findMembers(){
        return memberRepositroy.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepositroy.findById(memberId);
    }
}
