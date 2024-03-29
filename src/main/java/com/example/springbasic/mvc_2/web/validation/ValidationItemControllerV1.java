package com.example.springbasic.mvc_2.web.validation;

import com.example.springbasic.mvc_2.domain.validation.ItemV;
import com.example.springbasic.mvc_2.domain.validation.ValidationItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/validation/v1/items")
@RequiredArgsConstructor
public class ValidationItemControllerV1 {

    private final ValidationItemRepository validationItemRepository;

    @GetMapping
    public String items(Model model) {
        List<ItemV> itemVS = validationItemRepository.findAll();
        model.addAttribute("items", itemVS);
        return "validation/v1/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        ItemV itemV = validationItemRepository.findById(itemId);
        model.addAttribute("item", itemV);
        return "validation/v1/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new ItemV());
        return "validation/v1/addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute ItemV itemV, RedirectAttributes redirectAttributes, Model model) {

        //검증 오류 결과를 보관
        Map<String, String> errors = new HashMap<>();

        //검증 로직
        if (!StringUtils.hasText(itemV.getItemName())) {
            errors.put("itemName", "상품 이름은 필수입니다.");
        }
        if (itemV.getPrice() == null || itemV.getPrice() < 1000 || itemV.getPrice() > 1000000) {
            errors.put("price", "가격은 1,000 ~ 1,000,000 까지 허용합니다.");
        }
        if (itemV.getQuantity() == null || itemV.getQuantity() >= 9999) {
            errors.put("quantity", "수량은 최대 9,999 까지 허용합니다.");
        }

        //특정 필드가 아닌 복합 룰 검증
        if (itemV.getPrice() != null && itemV.getQuantity() != null) {
            int resultPrice = itemV.getPrice() * itemV.getQuantity();
            if (resultPrice < 10000) {
                errors.put("globalError", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice);
            }
        }

        //검증에 실패하면 다시 입력 폼으로
        if (!errors.isEmpty()) {
            log.info("errors = {} ", errors);
            model.addAttribute("errors", errors);
            return "validation/v1/addForm";
        }

        //성공 로직
        ItemV savedItemV = validationItemRepository.save(itemV);
        redirectAttributes.addAttribute("itemId", savedItemV.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v1/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        ItemV itemV = validationItemRepository.findById(itemId);
        model.addAttribute("item", itemV);
        return "validation/v1/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute ItemV itemV) {
        validationItemRepository.update(itemId, itemV);
        return "redirect:/validation/v1/items/{itemId}";
    }

}

