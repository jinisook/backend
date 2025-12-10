package com.example.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jpa.entity.Board;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    // title = ?
    List<Board> findByTitle(String title);

    // content = ?
    List<Board> findByContent(String content);

    // title like '%?'
    List<Board> findByTitleEndingWith(String title);

    // title liek '%?%' and id > 0 order by id desc
    List<Board> findByTitleContainingAndIdGreaterThanOrderByIdDesc(String title, Long id);

    // writer like '%?%'
    List<Board> findByWriterContaining(String writer);

    // title like '%?%' or content like '%?%'
    List<Board> findByTitleContainingOrContentContaining(String title, String content);

    // Query 사용 : entity 기준 - 대문자 중요
    // @Query("select b from Board b where b.title = ?1")
    @Query("select b from Board b where b.title = :title") // 물음표 사용안하고, 변수명 기입하는 방법
    List<Board> findByTitle2(String title);

    @Query("select b.title, b.writer from Board b where b.title like %:title%")
    List<Object[]> findByTitle3(@Param("title") String title); // Object[배열]

    @Query("select b from Board b where b.content = ?1")
    List<Board> findByContent2(String content);

    // @Query("select b from Board b where b.title like %?1")
    @Query("select b from Board b where b.title like %:title")
    List<Board> findByTitleEndingWith2(String title);

    @Query("select b from Board b where b.title like %?1 and b.id > ?2 order by b.id desc")
    List<Board> findByTitleAndId(String title, Long id);

    @Query("select b from Board b where b.writer like %?1%")
    List<Board> findByWriterContaining2(String writer);

    @Query("select b from Board b where b.title like %?1% or b.content like %?2%")
    List<Board> findByTitleOrContent(String title, String content);

    // @Query + nativeQuery => 소문자로 허용(sql 구문 형식)
    // @Query(value = "select b.* from boardtbl b where b.title like concat('%',
    // :title, '%') and b.id > :id order by b.id desc", nativeQuery = true)
    @NativeQuery(value = "select b.* from boardtbl b where b.title like concat('%', :title, '%') and b.id > :id order by b.id desc")
    List<Board> findByTitleAndId2(@Param("title") String title, @Param("id") Long id);
}
