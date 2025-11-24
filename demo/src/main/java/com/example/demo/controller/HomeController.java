package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Log4j2
@Controller
public class HomeController {
    // get 요청으로 들어올 때 -> @GetMapping
    @GetMapping("/home")
    public void getHome() {
        log.info("home 요청"); // == System.out.println() // @Log4j2를 써야 log.info 사용 가능
        log.info("컨트롤러");
    }

    @GetMapping("/add")
    public String getAdd() {
        return "result";
    }
    
    @GetMapping("/calc")
    public void getCalc() {
        log.info("calc get");
    }
    
    @PostMapping("/calc")
    public void postCalc(int num1) {
        log.info("calc post {} ",num1);
    }
    
    // @RequestParam("num1")
    
}
