package com.example.board.repository;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.member.entity.Member;
import com.example.board.member.repository.MemberRepository;
import com.example.board.post.dto.PageRequestDTO;
import com.example.board.post.entity.Board;
import com.example.board.post.repository.BoardRepository;
import com.example.board.reply.entity.Reply;
import com.example.board.reply.repository.ReplyRepository;

@Disabled
@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertMemberTest() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Member member = Member.builder()
                    .email("user" + i + "@gmail.com")
                    .password("1111")
                    .name("user" + i)
                    .build();
            memberRepository.save(member);
        });

    }

    @Test
    public void insertBoardTest() {
        IntStream.rangeClosed(1, 100).forEach(i -> {

            int idx = (int) (Math.random() * 10) + 1;
            Member member = Member.builder().email("user" + idx + "@gmail.com").build();

            Board board = Board.builder()
                    .title("title...." + i)
                    .content("content...." + i)
                    .writer(member)
                    .build();
            boardRepository.save(board);
        });

    }

    @Test
    public void insertReplyTest() {
        IntStream.rangeClosed(1, 100).forEach(i -> {

            long idx = (long) (Math.random() * 100) + 1;

            Board board = Board.builder().bno(idx).build();
            Reply reply = Reply.builder().text("reply...." + i).replyer("guest" + i).board(board).build();
            replyRepository.save(reply);
        });

    }

    @Test
    public void insertReplyTest2() {
        Board board = Board.builder().bno(501L).build();
        IntStream.rangeClosed(1, 15).forEach(i -> {

            Reply reply = Reply.builder().text("reply...." + i).replyer("guest" + i).board(board).build();

            replyRepository.save(reply);
        });

    }

    // board 읽기
    @Transactional(readOnly = true)
    @Test
    public void readBoardTest() {
        // JPA 제공
        List<Board> list = boardRepository.findAll();
        list.forEach(board -> {
            System.out.println(board);
            System.out.println(board.getWriter());
        });
    } // -> 비효율적인 sql 구문 발생 -> repository 에서 쿼리문 생성

    // repository 에서 쿼리문 생성 후
    @Test
    public void getBoardWithWriterListTest() {
        List<Object[]> result = boardRepository.getBoardWithWriterList();
        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));

        }
    }

    @Transactional(readOnly = true)
    @Test
    public void getBoardWithWriterTest() {
        // JPA
        Board board = boardRepository.findById(33L).get();
        System.out.println(board);
        // 댓글 가져오기
        // board 입장에서 reply 접근하려면, board 엔티티에 reply를 선언
        System.out.println(board.getReplies());
    }

    @Transactional(readOnly = true)
    @Test
    public void getBoardWithWriterTest2() {
        // JPQL(@Query)
        List<Object[]> result = boardRepository.getBoardWithReply(33L);
        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
        }

        result.forEach(obj -> System.out.println(Arrays.toString(obj)));
    }

    @Test
    public void getBoardWithReplyCountTest() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);
        // 기본적인 구문
        // for (Object[] objects : result) {
        // // System.out.println(Arrays.toString(objects));
        // Board board = (Board) objects[0];
        // Member member = (Member) objects[1];
        // Long replyCnt = (Long) objects[2];
        // System.out.println(board);
        // System.out.println(member);
        // System.out.println(replyCnt);

        // }

        // Stream<Object[]> data = result.get();
        // Stream<Object[]> data2 = result.getContent().stream(); // getContent()은 목록으로
        // 되어있는 것을 Stream으로

        result.get().forEach(obj -> {
            // System.out.println(Arrays.toString(obj));
            Board board = (Board) obj[0];
            Member member = (Member) obj[1];
            Long replyCnt = (Long) obj[2];
        });
        result.get().forEach(System.out::println);
        // result.get().forEach(System.out.println(Arrays::toString));
        // Arrays::toString -> 리턴 타입, Function 으로 받을 수 있음 Arrays를 받아서 String으로 만들어준다는 의미

        // Object[] => String
        Function<Object[], String> f = Arrays::toString;
        // Object[] objects
        result.get().forEach(obj -> System.out.println(f.apply(obj)));
    }

    @Test
    public void getBoardByBnoTest() {
        Object result = boardRepository.getBoardByBno(33L);

        Object[] arr = (Object[]) result;

        System.out.println(Arrays.toString(arr));
    }

    @Transactional
    @Test
    public void deleteByBnoTest() {
        replyRepository.deleteByBno(61L);
        boardRepository.deleteById(61L);

    }

    // querydsl
    @Test
    public void listTest() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(0)
                .size(20)
                .type("tcw")
                .keyword("title")
                .build();

        // Pageable pageable = PageRequest.of(pageRequestDTO.getPage(),
        // pageRequestDTO.getSize(),
        // Sort.by("bno").descending().and(Sort.by("title").ascending()));
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getSize());
        Page<Object[]> result = boardRepository.list(pageRequestDTO.getType(), pageRequestDTO.getKeyword(), pageable);
        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));

        }
    }

}
