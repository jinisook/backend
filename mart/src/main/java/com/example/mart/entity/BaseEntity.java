package com.example.mart.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@EntityListeners(value = AuditingEntityListener.class)
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
    @CreatedDate // 날짜 자동으로(스프링부트에서 만든 거 사용) - 생성된 날짜
    @Column(updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate // 수정될 대마다 항상 자동 업데이트 - 마지막 수정 날짜
    private LocalDateTime updateDate;
}
