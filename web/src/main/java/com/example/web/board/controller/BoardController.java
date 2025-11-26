package com.example.web.board.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.web.board.dto.BoardDTO;
import com.example.web.board.dto.BoardDTO.BoardDTOBuilder;




@Log4j2
@Controller
@RequestMapping("/board")
public class BoardController {
    @GetMapping("/list")
    public void getList(Model model) {
        log.info("/board/list 요청");

        // BoardDTO dto = new BoardDTO(1L, "스프링부트", "성이름", LocalDate.now());

        List<BoardDTO> list = new ArrayList<>();
        for (Long i = 1L; i < 21; i++) {
            // Builder 패턴 적용
            BoardDTO dto = BoardDTO.builder()
            .id(i)
            .title("스프링 부트"+i)
            .writer("토쿠토쿠")
            .regDate(LocalDateTime.now())
            .build();

            list.add(dto);
            
        }


        model.addAttribute("list", list);

    }
    @GetMapping("/read")
    public void getRead(@RequestParam ("id") Long id) {
        log.info("read 요청 {}", id);
    }
    
    
}
