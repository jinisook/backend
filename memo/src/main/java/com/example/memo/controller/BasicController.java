package com.example.memo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.memo.dto.MemoDTO;
import com.example.memo.service.MemoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
// @RequestMapping("/memo2")
@Log4j2
@RestController // 데이터(json)만 주고 받기
public class BasicController {
    // 자바 객체 <===> json

    private final MemoService memoService;

    @GetMapping("/hello")
    public String getHello() {
        return "hello world"; // 문자열은 브라우저 해석 가능
    }

    // @PathVariable
    // http://localhost:8080/sample1/5
    @GetMapping("/sample1/{id}")
    public MemoDTO getSample(@PathVariable Long id) {
        MemoDTO dto = memoService.read(id);
        return dto;
    }

    @GetMapping("/list")
    public List<MemoDTO> getList(Model model) {
        log.info("전체 메모 요청");
        List<MemoDTO> list = memoService.readAll();
        return list;
    }

}
