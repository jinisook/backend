package com.example.jpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Board;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    // Board 10개 삽입
    @Test
    public void insertTest() {
        for (int i = 1; i < 11; i++) {
            Board board = Board.builder()
                    .title("board title" + i)
                    .content("board content" + i)
                    .writer("board writer" + i)
                    .build();

            boardRepository.save(board);
        }
    }

    // 수정 : title, content
    // @Test
    // public void updatTest(){
    // Board board = boardRepository.findById(3L).get();
    // board.changeTitle("타이틀");
    // boardRepository.save(board);
    // }
    // @Test
    // public void updatTest2(){
    // Board board = boardRepository.findById(5L).get();
    // board.changeContent("콘텐츠");
    // boardRepository.save(board);
    // }

    // 한 번에 쓰는 방법
    @Test
    public void updatTest() {
        Board board = boardRepository.findById(3L).get();
        board.changeTitle("타이틀");
        board.changeContent("콘텐츠");
        boardRepository.save(board);
    }

    // 삭제
    @Test
    public void deleteTest() {
        boardRepository.deleteById(8L);
    }

    // 조회
    @Test
    public void readTest() {
        System.out.println(boardRepository.findById(2L));
    }

    @Test
    public void readTest2() {
        boardRepository.findAll().forEach(board -> System.out.println(board));
    }

}
