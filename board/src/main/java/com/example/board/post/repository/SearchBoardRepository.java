package com.example.board.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchBoardRepository {
    List<Object[]> list();

}
