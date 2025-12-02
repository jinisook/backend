package com.example.student.service;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.student.dto.StudentDTO;
import com.example.student.entity.constant.Grade;

@Disabled
@SpringBootTest
public class StudentServiceTest {
    @Autowired
    private StudentService studentService;

    @Test
    public void testInsert(){
        StudentDTO dto = StudentDTO.builder()
        .name("토쿠노")
        .gender("M")
        .addr("성수")
        .grade(Grade.FRESHMAN)
        .build();
        System.out.println(studentService.insert(dto));
    }

    @Test
    public void testRead(){
        System.out.println(studentService.read(4L));
    }

    @Test
    public void testReadAll(){
        List<StudentDTO> list = studentService.readAll();
        for (StudentDTO studentDTO : list) {
            System.out.println(studentDTO);
        }
    }

    @Test
    public void testUpdate(){
        StudentDTO dto = StudentDTO.builder()
        .id(5L)
        .name("토쿠노") // 이름 수정 안하면 그대로 입력 가능
        .grade(Grade.FRESHMAN)
        .build();
        System.out.println(studentService.update(dto));
    }


    @Test
    public void testDelete(){
        studentService.delete(3L);

    }
}
