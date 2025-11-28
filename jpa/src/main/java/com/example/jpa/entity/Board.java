package com.example.jpa.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.jpa.entity.constant.ItemSellStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@EntityListeners(value = AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@ToString
@Table(name = "boardtbl")
@Entity
public class Board {
    // id(자동 순번), 제목(title), 내용(content - 1500자), 작성자(writer - 20자)
    // 작성일, 수정일

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1500)
    private String content;
    
    @Column(nullable = false, length = 20)
    private String writer;

    @CreatedDate // 날짜 자동으로(스프링부트에서 만든 거 사용) - 생성된 날짜
    private LocalDateTime createDate;

    @LastModifiedDate // 수정될 대마다 항상 자동 업데이트 - 마지막 수정 날짜
    private LocalDateTime updateDate;

    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }


}
