package com.example.club.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.example.club.entity.Member;
import com.example.club.entity.constant.ClubMemberRole;

// @Disabled
@SpringBootTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertTest() {
        // 회원 10명 생성 + 권한 부여(USER, MANAGER, ADMIN - enum)
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Member member = Member.builder()
                    .email("user" + i + "@gmail.com")
                    .name("user" + i)
                    .fromSocial(false)
                    .password(passwordEncoder.encode("1111"))
                    .build();

            member.addMemberRole(ClubMemberRole.USER);

            if (i > 8) {
                member.addMemberRole(ClubMemberRole.MANAGER);
            }
            if (i > 9) {
                member.addMemberRole(ClubMemberRole.ADMIN);
            }
            memberRepository.save(member);
        });
    }

    // @Transactional
    @Test
    public void testLogin() {
        Member member = memberRepository.findByEmailAndFromSocial("user10@gmail.com", false).get();
        System.out.println(member);
    }
}
