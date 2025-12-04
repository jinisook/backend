package com.example.book.dto;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "ISBN 입력은 필수입니다.")
    private String isbn;

    @NotBlank(message = "도서 제목 입력은 필수입니다.")
    private String title;

    // 가격 0이상 1000이하
    // @Max(value = 10000000, message = "가격은 최대 천 만원입니다.")
    // @Min(value = 1, message = "최소 가격에 맞지 않습니다.")
    @Range(min = 0, max = 10000000, message = "가격은 0 ~ 10,000,000 사이입니다.")
    @NotNull(message = "도서 가격 입력은 필수입니다.")
    private Integer price; // notnull - Integer 사용

    @NotBlank(message = "도서 저자 입력은 필수입니다.")
    private String author;

    private String description;
}
