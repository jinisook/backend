package com.example.board.post.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.board.post.entity.Board;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, SearchBoardRepository {

    // @Query 문
    // on 구문 생략 기준 : 일치하는 컬럼
    // b.writer -> Board 내 멤버변수로 선언했기 때문에
    @Query("select b,m from Board b join b.writer m")
    List<Object[]> getBoardWithWriterList();

    // b.writer 처럼 Board 내에 Reply 가 없으니 on 구문 필요
    // Reply 내에는 board 선언 되어 있기 때문 r.board 가능
    // @Query("select b,m from Board b left join Reply r on r.board = b where
    // b.bno=?1")
    // bno를 사용해서 댓글 가져오기(삭제)
    @Query("select b,r from Board b left join Reply r on r.board = b where b.bno = :bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);

    // service - getRow - 하나 조회
    @Query("select b,m,count(r) from Board b left join b.writer m left join Reply r on r.board = b where b.bno = :bno")
    Object getBoardByBno(@Param("bno") Long bno);

    // 목록화면에 사용할 것 => 페이지 나누기 필요
    // 두줄 이상 쓸 때 각각의 구문 알려주기(value= , countQuery=)
    // 처음에만 사용(search 를 위해 변경)
    @Query(value = "select b,m,count(r) from Board b left join b.writer m left join Reply r on r.board = b group by b", countQuery = "select count(b) from Board b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);
}
