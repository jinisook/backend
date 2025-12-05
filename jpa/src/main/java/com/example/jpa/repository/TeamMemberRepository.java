package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.entity.Team;
import com.example.jpa.entity.TeamMember;
import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    // 팀 정보를 이용해 팀원 찾기
    List<TeamMember> findByTeam(Team team); // select * from team_member tm WHERE tm.team_id = 1; 외래키로 찾고 싶을 때

}
