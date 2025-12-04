package com.example.book.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.book.dto.BookDTO;
import com.example.book.entity.Book;
import com.example.book.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository; // repository 무조건 둬야함\

    private final ModelMapper mapper;

    // CRUD 메소드 호출하는 서비스 메소드

    public String create(BookDTO bookDTO) {
        // bookDTO => entity 변경
        // 1. 직접 코드 작성
        // 2. ModelMapper 라이브러리 사용
        // Book book = mapper.map(bookDTO, Book.class); -> 변수사용
        // bookRepository.save(book);
        return bookRepository.save(mapper.map(bookDTO, Book.class)).getTitle();
    }

    // 'R'ead(하나만 조회, 여러 개 조회)
    // 검색 : title => %자바% -> 여러 개 조회
    // isbn => (unique = true) -> 하나만 조희
    // id => (pk) -> 하나만 조희
    public List<BookDTO> readTitle(String title) {
        List<Book> result = bookRepository.findByTitleContaining(title);

        // List<Book> => List<BookDTO> 변경 후 리턴
        // List<BookDTO> list = new ArrayList<>();
        // result.forEach(book -> {
        // mapper.map(book, BookDTO.class);
        // });

        return result.stream().map(book -> mapper.map(book, BookDTO.class)).collect(Collectors.toList());
    }

    public BookDTO readIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn).orElseThrow();

        return mapper.map(book, BookDTO.class);

    }

    public BookDTO readId(Long id) {
        Book book = bookRepository.findById(id).orElseThrow();

        return mapper.map(book, BookDTO.class);

    }

    public Long update(BookDTO upDto) {
        Book book = bookRepository.findById(upDto.getId()).orElseThrow();
        book.changePrice(upDto.getPrice());
        book.chageDescription(upDto.getDescription());

        return bookRepository.save(book).getId();

    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public List<BookDTO> getList() {
        List<Book> result = bookRepository.findAll();

        return result.stream()
                .map(book -> mapper.map(book, BookDTO.class))
                .collect(Collectors.toList());

        // List<BookDTO> list = result.stream()
        // .map(book -> mapper.map(book, BookDTO.class))
        // .collect(Collectors.toList());

        // return list;
    }

}
