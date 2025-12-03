package com.example.book.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.book.entity.Book;

// DAO 역할
public interface BookRepository extends JpaRepository<Book, Long> { // <entity 이름, id 타입>

    Optional<Book> findByIsbn(String isbn); // => where isbn = 'A1000'

    List<Book> findByTitleContaining(String title); // => where title = '자바' // unique 때문에 정확인 title /findByTitleLike(X)
}
