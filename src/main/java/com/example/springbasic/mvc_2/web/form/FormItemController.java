package com.example.springbasic.mvc_2.web.form;

import com.example.springbasic.mvc_2.domain.item.DeliveryCode;
import com.example.springbasic.mvc_2.domain.item.FormItem;
import com.example.springbasic.mvc_2.domain.item.ItemRepository;
import com.example.springbasic.mvc_2.domain.item.ItemType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/form/items")
@RequiredArgsConstructor
@Slf4j
public class FormItemController {

    private final ItemRepository itemRepository;

    @ModelAttribute("regions")
    public Map<String, String> regions() {
        Map<String, String> regions = new LinkedHashMap<>();
        regions.put("SEOUL", "서울");
        regions.put("BUSAN", "부산");
        regions.put("JEJU", "제주");
        return regions;
    }

    @ModelAttribute("itemTypes")
    public ItemType[] itemTypes() {
        return ItemType.values();
    }

    @ModelAttribute("deliveryCodes")
    public List<DeliveryCode> deliveryCodes() {
        List<DeliveryCode> deliveryCodes = new ArrayList<>();
        deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
        deliveryCodes.add(new DeliveryCode("NORMAL", "일반 배송"));
        deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));
        return deliveryCodes;
    }

    @GetMapping
    public String items(Model model) {

        List<FormItem> formItems = itemRepository.findAll();
        model.addAttribute("items", formItems);
        return "form/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        FormItem formItem = itemRepository.findById(itemId);
        model.addAttribute("item", formItem);
        return "form/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new FormItem());
        return "form/addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute FormItem formItem, RedirectAttributes redirectAttributes) {

        log.info("item.open={}", formItem.getOpen());
        log.info("item.regions={}", formItem.getRegions());
        log.info("item.itemType={}", formItem.getItemType());

        FormItem savedFormItem = itemRepository.save(formItem);
        redirectAttributes.addAttribute("itemId", savedFormItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/form/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        FormItem formItem = itemRepository.findById(itemId);
        model.addAttribute("item", formItem);

        return "form/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute FormItem formItem) {
        itemRepository.update(itemId, formItem);
        return "redirect:/form/items/{itemId}";
    }

}

