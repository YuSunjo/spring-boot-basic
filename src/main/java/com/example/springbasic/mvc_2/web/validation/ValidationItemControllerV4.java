package com.example.springbasic.mvc_2.web.validation;

import com.example.springbasic.mvc_2.domain.validation.ItemV;
import com.example.springbasic.mvc_2.domain.validation.ValidationItemRepository;
import com.example.springbasic.mvc_2.web.validation.form.ItemSaveForm;
import com.example.springbasic.mvc_2.web.validation.form.ItemUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/validation/v4/items")
@RequiredArgsConstructor
public class ValidationItemControllerV4 {

    private final ValidationItemRepository validationItemRepository;

    @GetMapping
    public String items(Model model) {
        List<ItemV> itemVS = validationItemRepository.findAll();
        model.addAttribute("items", itemVS);
        return "validation/v4/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        ItemV itemV = validationItemRepository.findById(itemId);
        model.addAttribute("item", itemV);
        return "validation/v4/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new ItemV());
        return "validation/v4/addForm";
    }

    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute("item") ItemSaveForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        //특정 필드가 아닌 복합 룰 검증
        if (form.getPrice() != null && form.getQuantity() != null) {
            int resultPrice = form.getPrice() * form.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            return "validation/v4/addForm";
        }

        //성공 로직
        ItemV itemV = new ItemV();
        itemV.setItemName(form.getItemName());
        itemV.setPrice(form.getPrice());
        itemV.setQuantity(form.getQuantity());

        ItemV savedItemV = validationItemRepository.save(itemV);
        redirectAttributes.addAttribute("itemId", savedItemV.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v4/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        ItemV itemV = validationItemRepository.findById(itemId);
        model.addAttribute("item", itemV);
        return "validation/v4/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @Validated @ModelAttribute("item") ItemUpdateForm form, BindingResult bindingResult) {

        //특정 필드가 아닌 복합 룰 검증
        if (form.getPrice() != null && form.getQuantity() != null) {
            int resultPrice = form.getPrice() * form.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "validation/v4/editForm";
        }

        ItemV itemVParam = new ItemV();
        itemVParam.setItemName(form.getItemName());
        itemVParam.setPrice(form.getPrice());
        itemVParam.setQuantity(form.getQuantity());

        validationItemRepository.update(itemId, itemVParam);
        return "redirect:/validation/v4/items/{itemId}";
    }

}

