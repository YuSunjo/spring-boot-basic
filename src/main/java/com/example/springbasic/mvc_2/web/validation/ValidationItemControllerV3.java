package com.example.springbasic.mvc_2.web.validation;

import com.example.springbasic.mvc_2.domain.validation.ItemV;
import com.example.springbasic.mvc_2.domain.validation.ValidationItemRepository;
import com.example.springbasic.mvc_2.domain.validation.SaveCheck;
import com.example.springbasic.mvc_2.domain.validation.UpdateCheck;
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
@RequestMapping("/validation/v3/items")
@RequiredArgsConstructor
public class ValidationItemControllerV3 {

    private final ValidationItemRepository validationItemRepository;

    @GetMapping
    public String items(Model model) {
        List<ItemV> itemVS = validationItemRepository.findAll();
        model.addAttribute("items", itemVS);
        return "validation/v3/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        ItemV itemV = validationItemRepository.findById(itemId);
        model.addAttribute("item", itemV);
        return "validation/v3/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new ItemV());
        return "validation/v3/addForm";
    }

//    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute ItemV itemV, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        //특정 필드가 아닌 복합 룰 검증
        if (itemV.getPrice() != null && itemV.getQuantity() != null) {
            int resultPrice = itemV.getPrice() * itemV.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            return "validation/v3/addForm";
        }

        //성공 로직
        ItemV savedItemV = validationItemRepository.save(itemV);
        redirectAttributes.addAttribute("itemId", savedItemV.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v3/items/{itemId}";
    }

    @PostMapping("/add")
    public String addItem2(@Validated(SaveCheck.class) @ModelAttribute ItemV itemV, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        //특정 필드가 아닌 복합 룰 검증
        if (itemV.getPrice() != null && itemV.getQuantity() != null) {
            int resultPrice = itemV.getPrice() * itemV.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            return "validation/v3/addForm";
        }

        //성공 로직
        ItemV savedItemV = validationItemRepository.save(itemV);
        redirectAttributes.addAttribute("itemId", savedItemV.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v3/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        ItemV itemV = validationItemRepository.findById(itemId);
        model.addAttribute("item", itemV);
        return "validation/v3/editForm";
    }

//    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @Validated @ModelAttribute ItemV itemV, BindingResult bindingResult) {

        //특정 필드가 아닌 복합 룰 검증
        if (itemV.getPrice() != null && itemV.getQuantity() != null) {
            int resultPrice = itemV.getPrice() * itemV.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "validation/v3/editForm";
        }

        validationItemRepository.update(itemId, itemV);
        return "redirect:/validation/v3/items/{itemId}";
    }

    @PostMapping("/{itemId}/edit")
    public String editV2(@PathVariable Long itemId, @Validated(UpdateCheck.class) @ModelAttribute ItemV itemV, BindingResult bindingResult) {

        //특정 필드가 아닌 복합 룰 검증
        if (itemV.getPrice() != null && itemV.getQuantity() != null) {
            int resultPrice = itemV.getPrice() * itemV.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "validation/v3/editForm";
        }

        validationItemRepository.update(itemId, itemV);
        return "redirect:/validation/v3/items/{itemId}";
    }

}

