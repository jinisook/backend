package com.example.board.post.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.board.member.entity.Member;
import com.example.board.reply.entity.Reply;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@ToString(exclude = { "writer", "replies" })
@Table(name = "boardtbl")
@Entity
public class Board extends BaseEntity {
    // id(자동 순번), 제목(title), 내용(content - 1500자), 작성자(writer - 20자)
    // 작성일, 수정일

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long bno;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1500)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "email")
    private Member writer;

    // board -> reply 접근하려면
    @Builder.Default
    @OneToMany(mappedBy = "board")
    private List<Reply> replies = new ArrayList<>();

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }

}
