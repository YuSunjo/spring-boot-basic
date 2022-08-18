package com.example.springbasic.mvc_2.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    private static final Map<Long, FormItem> store = new HashMap<>(); //static
    private static long sequence = 0L; //static

    public FormItem save(FormItem formItem) {
        formItem.setId(++sequence);
        store.put(formItem.getId(), formItem);
        return formItem;
    }

    public FormItem findById(Long id) {
        return store.get(id);
    }

    public List<FormItem> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, FormItem updateParam) {
        FormItem findFormItem = findById(itemId);
        findFormItem.setItemName(updateParam.getItemName());
        findFormItem.setPrice(updateParam.getPrice());
        findFormItem.setQuantity(updateParam.getQuantity());
        findFormItem.setOpen(updateParam.getOpen());
        findFormItem.setRegions(updateParam.getRegions());
        findFormItem.setItemType(updateParam.getItemType());
        findFormItem.setDeliveryCode(updateParam.getDeliveryCode());
    }

    public void clearStore() {
        store.clear();
    }

}
