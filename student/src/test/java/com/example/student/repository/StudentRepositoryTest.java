package com.example.student.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.student.entity.Student;
import com.example.student.entity.constant.Grade;

@Disabled
@SpringBootTest
public class StudentRepositoryTest {
    
    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void deleteTest(){
        // Student student = studentRepository.findById(1L).get(); 
        // studentRepository.delete(student);
        studentRepository.deleteById(1L);
    }

    @Test
    public void readTest(){
        Student student = studentRepository.findById(2L).get(); // 한명 읽어오기 방법1
        System.out.println(student);
    }
    
    @Test // 전체 학생 조회
    public void readTest2(){
        List<Student> students = studentRepository.findAll();
        for (Student student : students) {
            System.out.println(student);
        }
    }

    @Test
    public void updateTest(){ 
        // Entity
        // update student set 수정컬럼=값 wwhere id=1;
        Optional<Student> result = studentRepository.findById(1L); //Optional<Student> -> null일 수도 있음을 의미 하는 코드
        Student student = result.get();
        // student.changetName("김원필");
        // grade 변경
        student.changeGrade(Grade.FRESHMAN);

   
        // insert(c), update(u) 작업 시 호출
        studentRepository.save(student);
    }

    @Test
    public void insertTest(){

        for (int i = 1; i < 11; i++) {
            Student student = Student.builder()
            .name("유우시"+i)
            .addr("서울")
            .gender("M")
            .grade(Grade.JUNIOR)
            .build();
    
            // insert(c), update(u) 작업 시 호출
            studentRepository.save(student);
            
        }
        
        // Entity

        // delete from ~ 호출
        // studentRepository.delete(student);
        // studentRepository.deleteById(student);

        // select * from where id =1;
        // studentRepository.findById(null);
        // select * from ;
        // studentRepository.findAll();

    }
}
