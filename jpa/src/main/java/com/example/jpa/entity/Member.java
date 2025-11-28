package com.example.jpa.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.jpa.entity.constant.RoleType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@EntityListeners(value = AuditingEntityListener.class)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="membertbl")
@Entity
public class Member {
    // 아이디, 이름(필수), 나이(필수), 역할(MEMBER, ADMIN), 가입일자, 수정일자, 자기소개
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String userId;
    
    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;

    @CreatedDate // 날짜 자동으로(스프링부트에서 만든 거 사용) - 생성된 날짜
    private LocalDateTime createDate;

    @LastModifiedDate // 수정될 대마다 항상 자동 업데이트 - 마지막 수정 날짜
    private LocalDateTime updateDate;

    
    // CLOB 지정한다면 @LOB
    // @Lob
    @Column(length = 2000)
    private String description;

    public void changeRole(RoleType role){
        this.role = role;
    }
    


}
