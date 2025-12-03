package com.example.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
// @Getter
// @Setter
// @ToString
@Data
public class BookDTO {
    private Long id;
    private String isbn;
    private String title;
    private int price;
    private String author;
}
