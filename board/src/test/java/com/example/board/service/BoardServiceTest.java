package com.example.board.service;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.post.dto.PageRequestDTO;
import com.example.board.post.entity.Board;
import com.example.board.post.service.BoardService;

// @Disabled
@SpringBootTest
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Transactional(readOnly = true)
    @Test
    public void getListTest() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();
        Page<Board> result = boardService.getList(pageRequestDTO);

        List<Board> boards = result.getContent();
        boards.forEach(board -> {
            System.out.println(board);
            System.out.println(board.getWriter());

        });

        System.out.println(result.getContent());
        System.out.println("전체 행 개수 " + result.getTotalElements());
    }
}
