package com.example.memo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.memo.dto.MemoDTO;
import com.example.memo.service.MemoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RequestMapping("/memo")
@Log4j2
@Controller // template 필요
public class MemoController {

    private final MemoService memoService;

    @GetMapping("/create2")
    public void getCreate2() {
        log.info("추가 rest 페이지 요청");
    }

    @GetMapping({ "/read2", "/modify2" })
    public void getRead3(@RequestParam("id") Long id, Model model) {
        log.info("특정 메모 요청 {} ", id);
        MemoDTO dto = memoService.read(id);
        model.addAttribute("dto", dto);
        model.addAttribute("id", id);
    }

    @GetMapping("/list2")
    public void getList2() {
        log.info("전체 메모 요청");
    }

    @GetMapping("/list")
    public void getList(Model model) {
        log.info("전체 메모 요청");
        List<MemoDTO> list = memoService.readAll();
        model.addAttribute("list", list);
    }

    @GetMapping({ "/read", "/modify" })
    public void getRead(@RequestParam("id") Long id, Model model) { // dto를 화면에 사용 -> Model
        log.info("memo id {}", id);

        MemoDTO dto = memoService.read(id);
        model.addAttribute("dto", dto);

        // /memo/read?id=1
        // /memo/modify?id=1

    }
    // read, modify 둘 다 같은 일을 하기 때문에, 한곳 작성
    // @GetMapping("/modify")
    // public void getModify(@RequestParam Long id, Model model) { // dto를 화면에 사용 ->
    // Model
    // log.info("memo id {}",id);

    // MemoDTO dto = memoService.read(id);
    // model.addAttribute("dto", dto);

    // }

    @PostMapping("/modify")
    public String postModify(@ModelAttribute("dto") MemoDTO dto, RedirectAttributes rttr) {
        log.info("memo 수정 {}", dto);

        Long id = memoService.modify(dto);

        // /memo/read?id=1 이동
        rttr.addAttribute("id", id);
        return "redirect:/memo/read";
    }

    @PostMapping("/remove")
    public String postRemove(@RequestParam("id") Long id, RedirectAttributes rttr) {
        log.info("memo remove id {}", id);
        memoService.remove(id);
        // 삭제 후 목록 보여주기
        rttr.addFlashAttribute("msg", "메모 삭제 완료!");
        return "redirect:/memo/list"; // 컨트롤러 실행
        // return "/memo/list"; -> list.html 보여주기 => 의미X
    }

    @GetMapping("/create")
    public void getCreate(@ModelAttribute("dto") MemoDTO dto) {
        log.info("추가 페이지 요청");
    }

    @PostMapping("/create")
    public String postCreate(@ModelAttribute("dto") @Valid MemoDTO dto, BindingResult result, RedirectAttributes rttr) {
        log.info("추가 요청 {}", dto);
        // 유효성 검증 조건에 일치하지 않는 경우 (에러 발생 시)
        if (result.hasErrors()) {
            return "/memo/create";
        }
        // 일치하는 경우
        Long id = memoService.insert(dto);
        rttr.addFlashAttribute("msg", id + "번 메모 삽입!");
        return "redirect:/memo/list";
    }

    // ====================
    // 일반 컨트롤로러를 객체로 보내는 방법
    @ResponseBody // return 값이 데이터임을 명시 - 일반컨트롤러일때 사용
    @GetMapping("/hello")
    public String getHello() {
        return "hello world"; // 문자열은 브라우저 해석 가능
    }

    @ResponseBody
    @GetMapping("/sample1/{id}")
    public MemoDTO getRead(@PathVariable("id") Long id) {
        MemoDTO dto = memoService.read(id);
        return dto;
    }

    // ResponseEntity : 데이터 + 상세코드(200, 400, 500)
    @GetMapping("/sample1/list")
    public ResponseEntity<List<MemoDTO>> getRead2() {
        List<MemoDTO> list = memoService.readAll();
        return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
    }

}
