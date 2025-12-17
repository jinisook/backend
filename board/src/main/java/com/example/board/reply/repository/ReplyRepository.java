package com.example.board.reply.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.board.reply.entity.Reply;
import java.util.List;
import com.example.board.post.entity.Board;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Modifying
    @Query("delete from Reply r where r.board.bno = :bno") // .bno => board != long 타입
    public void deleteByBno(@Param("bno") Long bno);

    List<Reply> findByBoardOrderByRno(Board board);

}
