package com.example.jpa.repository;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    // 쿼리메소드
    @Test
    public void testFindBy() {
        List<Board> list = boardRepository.findByTitle("board title1");
        System.out.println("findByTitle(board title1)" + list);

        list = boardRepository.findByContent("board content2");
        System.out.println("findByContent(board content2)" + list);

        list = boardRepository.findByTitleEndingWith("6");
        System.out.println("findByTitleEndingWith(6)" + list);

        list = boardRepository.findByTitleContainingAndIdGreaterThanOrderByIdDesc("이", 0L);
        System.out.println("findByTitleContainingAndIdGreaterThanOrderByIdDesc(이, 0L)" + list);

        list = boardRepository.findByWriterContaining("w");
        System.out.println("findByWriterContaining(w)" + list);

        list = boardRepository.findByTitleContainingOrContentContaining("이", "텐");
        System.out.println("findByTitleContainingOrContentContaining(이, 텐))" + list);

    }

    @Test
    public void queryMethodTest() {
        System.out.println(boardRepository.findByTitle2("board title1"));
        System.out.println(boardRepository.findByContent2("board title2"));
        System.out.println(boardRepository.findByTitleEndingWith2("6"));
        System.out.println(boardRepository.findByTitleAndId("이", 0L));
        System.out.println(boardRepository.findByWriterContaining2("w"));
        System.out.println(boardRepository.findByTitleOrContent("이", "텐"));
    }

    @Test
    public void queryMethodTest2() {
        System.out.println(boardRepository.findByTitleAndId2("이", 0L));
    }

    @Test
    public void queryMethodTest3() {

        // @Query("select b.title, b.writer from Board b where b.title like %:title%")
        // List<Object[]> findByTitle3(@Param("title") String title); // Object[배열]

        List<Object[]> result = boardRepository.findByTitle3("title");
        for (Object[] objects : result) {
            // System.out.println(Arrays.toString(objects));
            String title = (String) objects[0];
            String writer = (String) objects[1];
            System.out.println(title + " " + writer);

        }
    }

}
