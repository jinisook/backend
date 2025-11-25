package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Log4j2
@Controller
@RequestMapping("/board")
public class BoardController {
    @GetMapping("/add") // == http://localhost:8080/board/add
    public void getAdd() {
        log.info("/board/add 요청");
        // templates/board/add.html
    }
    
    @GetMapping("/modify") // == http://localhost:8080/board/modify"
    public void getModify() {
        log.info("/board/modify 요청");
        // templates/board/modify.html
    }
 
    @GetMapping("/read") // == http://localhost:8080/board/read
    public void getRead(@ModelAttribute("no") int no, Model model) {
        log.info("/board/read 요청 {}", no);
        // templates/board/read.html
        // model.addAttribute("no", no);
    }
 
    // 경로 중복 불가
    // @GetMapping("/board/add")
    // public void getAdd2() {
    //     log.info("/board/add 요청");
    // }
    
}
