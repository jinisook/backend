package com.example.student.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.student.entity.constant.Grade;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@EntityListeners(value = AuditingEntityListener.class)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "stutbl")
@Entity // == 이 클래스는 테이블과 연동되어 있음
public class Student {

    // @GeneratedValue(strategy = GenerationType.AUTO) => @GeneratedValue : default(Hibernate가 자동으로 셍성)
    // @SequenceGenerator(name="stu_seq_gen", sequenceName = "stu_seq", allocationSize = 1) - 오라클
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stu_seq_gen") - 오라클
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL(auto_incerement), Oracle(sequence) 
    @Id
    private Long id;

    // @Column(name = "sname", length = 50, nullable = false, unique = true) // notnull,unique
    @Column(columnDefinition = "varchar(50) not null") // sql 쿼리문 
    private String name;

    @Column
    private String addr;
    
    @Column(columnDefinition = "varchar(1) CONSTRAINT chk_gender CHECK (gender IN('M','F'))")
    private String gender;

    // grade => FRESHMAN, SOPHMORE, JUNIOR, SENIOR 
    @Enumerated(EnumType.STRING)
    @Column
    private Grade grade; 

    @CreatedDate // spring boot 설정 후 삽입
    private LocalDateTime createDateTime;

    @LastModifiedDate
    private LocalDateTime updateDateTime;

    public void changetName(String name) {
        this.name = name;
    }

    public void changeGrade(Grade grade) {
        this.grade = grade;
    }
}
