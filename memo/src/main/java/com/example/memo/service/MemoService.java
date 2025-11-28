package com.example.memo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.memo.dto.MemoDTO;
import com.example.memo.entity.Memo;
import com.example.memo.repository.MemoRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class MemoService {

    @Autowired
    private MemoRepository memoRepository;

    // 전체조회
    public List<MemoDTO> readAll(){
        List<Memo> memos = memoRepository.findAll();

        // Memo(Entity) (service => repository / repository => service)
        // ~~DTO : service => controller / controller => service
        // 리턴하기 전 Memo entity => MemoDTO 로 변경 후 리턴

        List<MemoDTO> list = new ArrayList<>();
        for (Memo memo : memos) {
            MemoDTO dto = MemoDTO.builder()
            .id(memo.getId())
            .text(memo.getText())
            .createDate(memo.getCreateDate())
            .updateDate(memo.getUpdateDate())
            .build();
            list.add(dto);
        }
        return list;
    }
}
