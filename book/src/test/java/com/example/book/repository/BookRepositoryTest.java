package com.example.book.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.example.book.entity.Book;
import com.example.book.entity.QBook;

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

    @Test
    public void testFindBy() {
        List<Book> list = bookRepository.findByAuthor("천인국0");
        System.out.println("findByAuthor(천인국0) " + list);

        list = bookRepository.findByAuthorEndingWith("0");
        System.out.println("findByAuthorEndingWith(0) " + list);

        list = bookRepository.findByAuthorStartingWith("천인");
        System.out.println("findByAuthorStartingWith(천인) " + list);

        list = bookRepository.findByAuthorContaining("인");
        System.out.println("findByAuthorContaining(인) " + list);

        list = bookRepository.findByPriceBetween(12000, 35000);
        System.out.println("findByPriceBetween " + list);
    }

    @Test
    public void pageTest() {
        // bookRepository.findAll(Pageable pageablenull) -> JpaRepository 상속 시 생김
        // limit ?, ? : 특정 범위만 가져오기
        // select count(b1_0.id) : 전체 행의 개수
        PageRequest pageRequest = PageRequest.of(0, 20);
        Page<Book> result = bookRepository.findAll(pageRequest);

        System.out.println("page size " + result.getSize());
        System.out.println("TotalPages " + result.getTotalPages());
        System.out.println("TotalElements " + result.getTotalElements());
        System.out.println("Content " + result.getContent());

    }

    // ###############
    // querydsl 라이브러리 추가 / QuerydslPredicateExecutor<Book> 상속
    // ###############
    @Test
    public void querydslTest() {

        QBook book = QBook.book;
        // Predicate predicate -> true/false

        // where b1_0.title=?
        System.out.println(bookRepository.findAll(book.title.eq("title1")));
        // where b1_0.title like %?%
        System.out.println(bookRepository.findAll(book.title.contains("파워")));
        // where b1_0.title like %?% and b1_0.id > ?
        System.out.println(bookRepository.findAll(book.title.contains("파워").and(book.id.gt(3L))));
        // where b1_0.title like %?% and b1_0.id > ? order by id desc
        System.out.println(
                bookRepository.findAll(book.title.contains("파워").and(book.id.gt(3L)), Sort.by("id").descending()));

        // where author '%천%' or title='%파워%'
        System.out.println(bookRepository.findAll(book.title.contains("파워").or(book.author.contains("천"))));

        // bookRepository.findAll(Predicate predicate, Pageable pageable)
        PageRequest pageRequest = PageRequest.of(0, 20);
        Page<Book> result = bookRepository.findAll(book.id.gt(0L), pageRequest);

    }

}
