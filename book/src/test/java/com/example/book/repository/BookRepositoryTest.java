package com.example.book.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.book.entity.Book;

import jakarta.persistence.EntityNotFoundException;

@Disabled
@SpringBootTest
public class BookRepositoryTest {

    @Autowired // BookRepository가 무엇인지 주입 해주는 것
    private BookRepository bookRepository;

    @Test
    public void insert() {
        // Book book = Book.builder.build(); == Book book = new Book();
        Book book = Book.builder()
                .isbn("A101010")
                .title("파워 자바")
                .author("천인국")
                .price(36000)
                .build();

        bookRepository.save(book);
    }

    @Test
    public void insert2() {

        IntStream.rangeClosed(0, 9)
                .forEach(i -> {
                    Book book = Book.builder()
                            .isbn("A101010" + i)
                            .title("파워 자바" + i)
                            .author("천인국" + i)
                            .price(36000)
                            .build();

                    bookRepository.save(book);
                });

    }

    @Test
    public void testRead() {
        // bookRepository.findById(1L).orElse(null);
        Book book = bookRepository.findById(1L).orElseThrow();
        // bookRepository.findById(1L).orElseThrow(EntityNotFoundException::new);
        // Optional<Book> result = bookRepository.findById(1L);
        // if (result.isPresent()) {
        // Book book = result.get();
        // }

        System.out.println(book);

    }

    @Test
    public void testRead2() {
        Book book = bookRepository.findByIsbn("A1010100").orElseThrow(EntityNotFoundException::new);

        List<Book> list = bookRepository.findByTitleContaining("파워");
        System.out.println(list);

    }

    @Test
    public void testModify() {
        Book book = bookRepository.findById(3L).orElseThrow();

        book.changePrice(30000);

        System.out.println(bookRepository.save(book));

    }

    @Test
    public void testDelete() {
        bookRepository.deleteById(10L);
    }

}
