package com.example.jpa.repository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.example.jpa.entity.Team;
import com.example.jpa.entity.TeamMember;

import jakarta.persistence.OneToMany;
import jakarta.transaction.Transactional;

@SpringBootTest
public class TeamRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Test
    public void insertTest() {

        Team team = Team.builder().name("team1").build();
        teamRepository.save(team);

        TeamMember member = TeamMember.builder().name("길동이").team(team).build();
        teamMemberRepository.save(member);
    }

    @Test
    public void insertTest2() {

        // 기존에 있는 팀에 저장하는 방법1
        // Team team = Team.builder().id(1L).build();
        // TeamMember member = TeamMember.builder().name("길금이").team(team).build();
        // teamMemberRepository.save(member);

        // 기존에 있는 팀에 저장하는 방법2
        Team team = teamRepository.findById(3L).get(); // <- 코드 확인
        TeamMember member = TeamMember.builder().name("홍홍홍").team(team).build();
        teamMemberRepository.save(member);

    }

    @Test
    public void insertTest3() {

        Team team = Team.builder().name("team3").build();
        teamRepository.save(team);
    }

    @Test
    public void readTest() {

        // Team team = Team.builder().id(1L).build();
        Team team = teamRepository.findById(1L).get();
        System.out.println(team); // Team(id=1, name=null)

        // 외래키가 적용된 테이블이기 때문에 join을 바로 해서 코드 실행
        TeamMember member = teamMemberRepository.findById(1L).get();
        System.out.println(member); // TeamMember(id=1, name=길동이, team=Team(1d=1, name=team1))

        // 팀원 => 팀 조회
        // System.out.println("팀 명 " + member.getTeam().getName());

        // 팀 => 팀원 조회 (X) -> @OneToMany로

    }

    @Test
    public void updateTest() {

        // 팀 이름 변경
        Team team = teamRepository.findById(1L).get();
        team.changeName("플라워");
        System.out.println(teamRepository.save(team));

        // 팀 변경
        // 1. 바꿀 대상 바꾸기
        TeamMember teamMember = teamMemberRepository.findById(2L).get();
        teamMember.changeTeam(Team.builder().id(2L).build());
        System.out.println(teamMemberRepository.save(teamMember));

    }

    @Test
    public void deleteTest() {

        // 1. 팀원 삭제
        // 2. 삭제하려고 하는 팀의 팀원들을 다른 팀으로 변경

        // 2. 팀 변경 -> 1) 팀원(팀 정보를 이용) 찾기
        List<TeamMember> result = teamMemberRepository.findByTeam(Team.builder().id(1L).build());
        // List -> forEach 구문 사용
        result.forEach(m -> {
            m.changeTeam(Team.builder().id(2L).build());
            // changeTeam 하고 저장
            teamMemberRepository.save(m);
        });

        // 팀 삭제 -> 팀원을 먼저 삭제 or 삭제하려고 하는 팀의 팀원들을 다른 팀으로 변경
        teamRepository.deleteById(1L);

    }

    @Test
    public void deleteTest2() {

        // 1. 팀원 삭제
        // 2. 삭제하려고 하는 팀의 팀원들을 다른 팀으로 변경

        // 1. 팀원 삭제 -> 1) 팀원(팀 정보를 이용) 찾기
        List<TeamMember> result = teamMemberRepository.findByTeam(Team.builder().id(2L).build());
        // List -> forEach 구문 사용
        result.forEach(m -> {
            teamMemberRepository.delete(m);
        });

        // 팀 삭제 -> 팀원을 먼저 삭제 or 삭제하려고 하는 팀의 팀원들을 다른 팀으로 변경
        teamRepository.deleteById(2L);

    }

    // ************************************************************
    // 팀 => 멤버 조회
    @Transactional
    @Test
    public void readTest2() {

        Team team = teamRepository.findById(3L).get();

        // 팀 => 팀원 조회
        // Team.java 내 구문
        // @OneToMany(mappedBy = "team")
        // private List<TeamMember> members = new ArrayList<>();
        System.out.println(team); // select * from teambl where id=3;
        // System.out.println(team.getMembers()); // select * from team_member where
        // team_id=3;

    }

    @Transactional
    @Test
    public void readTest3() {

        Team team = teamRepository.findById(3L).get();

        // 팀 => 팀원 조회(left join 처리)
        // left join 처리를 주기 위해서 @OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
        System.out.println(team);
        // System.out.println(team.getMembers());

    }

    @Transactional
    @Test
    public void readTest4() {

        TeamMember member = teamMemberRepository.findById(4L).get();
        System.out.println(member);
        // 멤버를 통해서 팀정보를 가져오고 싶을 때,
        System.out.println(member.getTeam());

    }

    // cascade 개념 적용
    @Test
    public void insertCascadeTest() {
        Team team = Team.builder().name("new").build();

        team.getMembers().add(TeamMember.builder().name("유우시").team(team).build());
        teamRepository.save(team);

    }

    @Test
    public void removeCascadeTest() {
        teamRepository.deleteById(4L);

    }

    // orphanRemoval = true 적용
    @Commit
    @Transactional // Rollback 개념
    @Test
    public void removeOrphanTest() {
        Team team = teamRepository.findById(3L).get();
        team.getMembers().remove(0);
        teamRepository.save(team);

    }

    @Commit
    @Transactional
    @Test
    public void updateCascadeTest() {
        // dirty checking 적용
        Team team = teamRepository.findById(5L).get();
        team.changeName("sunflower");
        TeamMember teamMember = team.getMembers().get(0);
        teamMember.changeName("토쿠노유우시");

        // teamRepository.save(team);

    }

}
