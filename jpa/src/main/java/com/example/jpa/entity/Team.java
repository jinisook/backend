package com.example.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(exclude = "members")
// @ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "teambl")
@Entity
public class Team {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "team_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Builder.Default // builder로 객체 생성할 때 자동으로,
    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER) // @ManyToOne이 주인이라는 것을 명시
    private List<TeamMember> members = new ArrayList<>();

    public void changeName(String name) {
        this.name = name;
    }

}
