package com.example.springbasic;

import com.example.springbasic.mvc_2.domain.item.FormItem;
import com.example.springbasic.mvc_2.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final ItemRepository itemRepository;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new FormItem("itemA", 10000, 10));
        itemRepository.save(new FormItem("itemB", 20000, 20));
    }

}