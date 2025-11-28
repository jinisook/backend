package com.example.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Member;
import com.example.jpa.entity.constant.RoleType;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertTest(){
        for (int i = 1; i < 11; i++) {
            Member member = Member.builder()
            .userId("userId"+i)
            .name("guest"+i)
            .role(RoleType.MEMBER)
            .build();

            memberRepository.save(member);


            
        }
    }

    @Test
    public void updateTest(){

        // userId 인 사람의 role 변경
        Optional<Member> result = memberRepository.findById(9L);
        result.ifPresent(member -> {
            member.changeRole(RoleType.ADMIN);
            memberRepository.save(member);
        });

    }

    @Test
    public void deleteTest(){
        memberRepository.deleteById(10L);
    }

    @Test
    public void readTest(){
        Optional<Member> result = memberRepository.findById(5L);
        result.ifPresent(member -> System.out.println(member));
    }
    
    @Test
    public void readTest2(){
        List<Member> members = memberRepository.findAll();
        members.forEach(member -> System.out.println(member));
    }

}
