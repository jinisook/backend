package com.example.memo.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class MemoDTO {
    private Long id;
    @NotBlank(message = "메모 내용은 필수입력 요소입니다.")
    private String text;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
