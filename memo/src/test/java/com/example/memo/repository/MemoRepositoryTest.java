package com.example.memo.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.memo.entity.Memo;

@SpringBootTest
public class MemoRepositoryTest {


    @Autowired
    private MemoRepository memoRepository;

    // CRUD 테스트
    @Test
    public void readTest2(){
        List<Memo> memos = memoRepository.findAll();
        for (Memo memo : memos){
            System.out.println(memo);
        }
    }

    @Test
    public void readTest(){
        Memo memo = memoRepository.findById(3L).get();
        System.out.println(memo);
    }

    @Test
    public void deleteTest(){
        memoRepository.deleteById(10L);
    }


    @Test
    public void updateTest(){
        // 3번 메모 text 수정
        Memo memo = memoRepository.findById(3L).get();

        memo.changeText("변경 text");
        memoRepository.save(memo);
    }




    @Test
    public void insertTest(){
        for (int i = 0; i <10; i++) {
            Memo memo = Memo.builder()
            .text("memo text"+i)
            .build();

            memoRepository.save(memo);
        }
    }
    
}
