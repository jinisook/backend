package com.example.board.post.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BoardDTO { // 화면용 - 화면과 일치 시키는 개념(entity - db랑 일치 시키는 개념)

    private Long bno;

    private String title;

    private String content;

    private String writerEmail; // 작성자 이메일
    private String writerName; // 작성자 이름

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    private int replyCnt; // 댓글 개수

}
