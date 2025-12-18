package com.example.board.post.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchBoardRepository {
    Page<Object[]> list(String type, String keyword, Pageable pageable);

    Object[] getBoardByBno(Long bno);
}
