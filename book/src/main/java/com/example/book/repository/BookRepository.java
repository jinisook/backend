package com.example.book.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.example.book.entity.Book;
import com.example.book.entity.QBook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

// DAO 역할
public interface BookRepository extends JpaRepository<Book, Long>, QuerydslPredicateExecutor<Book> {

    Optional<Book> findByIsbn(String isbn); // => where isbn = 'A1000'

    List<Book> findByTitleContaining(String title); // => where title like '%자바%'
    // unique 때문에 정확인 title /findByTitleLike(X)

    // where author = ''
    List<Book> findByAuthor(String author);

    // where author like '%이름'/'이름%'/'%이름%'
    // where author like '%영'
    List<Book> findByAuthorEndingWith(String author);

    // where author like '박%'
    List<Book> findByAuthorStartingWith(String author);

    // where author like '%진수%'
    List<Book> findByAuthorContaining(String author);

    // 도서가격이 12000이상 35000이하
    List<Book> findByPriceBetween(int startPrice, int endPrice);

    public default Predicate makePredicate(String type, String Keyword) {
        BooleanBuilder builder = new BooleanBuilder();
        QBook book = QBook.book;

        builder.and(book.id.gt(0)); // where b.id > 0
        return builder;
    }

}
