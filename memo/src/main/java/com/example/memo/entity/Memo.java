package com.example.memo.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
import lombok.ToString;

@EntityListeners(value = AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@Table(name = "memotbl") // -> 테이블 이름을 memo가 아닌 memotbl로 변경
@Entity // -> 테이블이랑 연결되어 있음
public class Memo {
    // 데이터베이스 테이블(memotbl) 컬럼 : mno, memo_text, create_update, update_date
    // 클래스 필드명 == 테이블 컬럼명 (선택사항)
    // 클래스 필드명 != 테이블 컬럼명 (선택사항) -> ( @Column(name="") 어노테이션 사용)

    @GeneratedValue(strategy = GenerationType.IDENTITY) // 숫자 증가 시 사용
    @Id // id는 무조건 있어야 됨 -> primary key(PK) 필요하기 때문
    @Column(name = "mno") // -> 테이블에서는 mno로 지정
    private Long id;

    @Column(nullable = false, name = "memo_text") // (nullable = fales) == not null 의미
    private String text; // db에서는 memo_text

    @CreatedDate // 날짜 자동으로(스프링부트에서 만든 거 사용) - 생성된 날짜
    private LocalDateTime createDate;

    @LastModifiedDate // 수정될 대마다 항상 자동 업데이트 - 마지막 수정 날짜
    private LocalDateTime updateDate;

    // text 수정 메소드
    public void changeText(String text) {
        this.text = text;
    }

}
