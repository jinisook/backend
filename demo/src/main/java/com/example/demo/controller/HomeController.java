package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.Info;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Log4j2
@Controller
public class HomeController {

    @GetMapping("/") // == http://localhost:8080
    public String getIndex(RedirectAttributes rttr) {
        // return "index";

        // ?key=value 보내고 싶을 때
        rttr.addAttribute("bno", 10);
        rttr.addAttribute("name", "유우시");
        rttr.addFlashAttribute("money", 1000);

        return "redirect:/home"; // redirect:/경로(컨트롤러가 가지고 있는 경로 중 하나)
    }
    // http://localhost:8080/home?bno=10


    // get 요청으로 들어올 때 -> @GetMapping
    @GetMapping("/home")
    public void getHome(@RequestParam("bno") int bno, @RequestParam("name") String name) {
        log.info("home 요청 {} {}", bno, name); // == System.out.println() // @Log4j2를 써야 log.info 사용 가능
        log.info("컨트롤러");
    }

    @GetMapping("/add")
    public String getAdd(
        @RequestParam("num1") int num1, 
        @RequestParam("op") String op, 
        @RequestParam("num2") int num2,
        Model model
    ) {
        log.info("사칙연산 요청 {} {} {}", num1, op, num2);
        int result = 0;
        switch (op) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                result = num1 / num2;
                break;
        }
        log.info("result =" + result);

        // Model : 컨트롤러가 뷰에 데이터를 전달하기 위해 사용하는 인터페이스
        // (key, value) : key 값은 중복 안되게 

        model.addAttribute("num1", num1);
        model.addAttribute("op", op);
        model.addAttribute("num2", num2);
        model.addAttribute("result", result);
        return "exam3";
    }
    
    @GetMapping("/calc")
    public void getCalc() {
        log.info("calc get");
    }
    
    @PostMapping("/calc")
    public void postCalc(
        @RequestParam(name = "num1", required = false, defaultValue = "0") int num1, 
        @RequestParam(name = "num2", required = false, defaultValue = "0") int num2) {
        log.info("calc post {} {}",num1, num2);
    }
    
// http://localhost:8080/info
@GetMapping("/info")
public void getInfo() {
    log.info("info.html 호출");
}

// 클라이언트가 입력한 값 가져오기(get, post)
// 1. 개별 처리
// @PostMapping("/info")
// public void postInfo(
//     @RequestParam("username") String username, 
//     @RequestParam("age") int age, 
//     @RequestParam("addr") String addr, 
//     @RequestParam("tel") String tel) {
//     log.info("info post");
//     log.info("{},{},{},{}", username, age, addr, tel);
// }

// 2. DTO 이용
// @PostMapping("/info")
// public void postInfo(Info info) {
//     log.info("info post");
//     log.info("{},{},{},{}", info.getUsername(),info.getAge(),info.getAddr(),info.getTel());
// }

// 3. HttpServletRequest 객체 사용
// 사용자가 요청할 때 사용하는 모든 정보 가지고 올 수 있음 : 브라우저 정보, 사용자 ip, 경로 추출
@PostMapping("/info")
public void postInfo(HttpServletRequest request) {
    log.info("info post");
    String username = request.getParameter("username");
    int age = Integer.parseInt(request.getParameter("age"));
    String addr = request.getParameter("addr");
    String tel = request.getParameter("tel");
    log.info("{},{},{},{}", username, age , addr, tel);
}


}
