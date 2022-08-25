package com.example.springbasic.mvc_2.domain.validation;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ValidationItemRepository {

    private static final Map<Long, ItemV> store = new HashMap<>(); //static
    private static long sequence = 0L; //static

    public ItemV save(ItemV itemV) {
        itemV.setId(++sequence);
        store.put(itemV.getId(), itemV);
        return itemV;
    }

    public ItemV findById(Long id) {
        return store.get(id);
    }

    public List<ItemV> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, ItemV updateParam) {
        ItemV findItemV = findById(itemId);
        findItemV.setItemName(updateParam.getItemName());
        findItemV.setPrice(updateParam.getPrice());
        findItemV.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }

}
