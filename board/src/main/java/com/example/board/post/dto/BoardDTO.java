package com.example.board.post.dto;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BoardDTO {

    private Long bno;

    private String title;

    private String content;

    private String email; // 작성자 이메일

}
