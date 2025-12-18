package com.example.club.entity;

import java.util.HashSet;
import java.util.Set;

import com.example.club.entity.constant.ClubMemberRole;

import groovy.transform.ToString;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
@Table(name = "Club_member")
@Entity
public class Member {

    @Id
    private String email;

    private String password;

    private String name;

    private boolean fromSocial;

    @ElementCollection(fetch = FetchType.LAZY) // member 1 : role N 의미
    @Builder.Default
    private Set<ClubMemberRole> roles = new HashSet<>();

    public void addMemberRole(ClubMemberRole role) {
        roles.add(role);
    }

}
