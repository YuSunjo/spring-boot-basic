package com.example.springbasic.mvc_2.web.validation;

import com.example.springbasic.mvc_2.domain.validation.ItemV;
import com.example.springbasic.mvc_2.domain.validation.ValidationItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor
public class ValidationItemControllerV2 {

    private final ValidationItemRepository validationItemRepository;
    private final ItemValidator itemValidator;

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(itemValidator);
    }

    @GetMapping
    public String items(Model model) {
        List<ItemV> itemVS = validationItemRepository.findAll();
        model.addAttribute("items", itemVS);
        return "validation/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        ItemV itemV = validationItemRepository.findById(itemId);
        model.addAttribute("item", itemV);
        return "validation/v2/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new ItemV());
        return "validation/v2/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(@ModelAttribute ItemV itemV, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        //검증 로직
        if (!StringUtils.hasText(itemV.getItemName())) {
            bindingResult.addError(new FieldError("item", "itemName", "상품 이름은 필수 입니다."));
        }
        if (itemV.getPrice() == null || itemV.getPrice() < 1000 || itemV.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item", "price", "가격은 1,000 ~ 1,000,000 까지 허용합니다."));
        }
        if (itemV.getQuantity() == null || itemV.getQuantity() >= 9999) {
            bindingResult.addError(new FieldError("item", "quantity", "수량은 최대 9,999 까지 허용합니다."));
        }

        //특정 필드가 아닌 복합 룰 검증
        if (itemV.getPrice() != null && itemV.getQuantity() != null) {
            int resultPrice = itemV.getPrice() * itemV.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.addError(new ObjectError("item", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice));
            }
        }

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            return "validation/v2/addForm";
        }

        //성공 로직
        ItemV savedItemV = validationItemRepository.save(itemV);
        redirectAttributes.addAttribute("itemId", savedItemV.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute ItemV itemV, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        //검증 로직
        if (!StringUtils.hasText(itemV.getItemName())) {
            bindingResult.addError(new FieldError("item", "itemName", itemV.getItemName(), false, null, null, "상품 이름은 필수 입니다."));
        }
        if (itemV.getPrice() == null || itemV.getPrice() < 1000 || itemV.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item", "price", itemV.getPrice(), false, null, null, "가격은 1,000 ~ 1,000,000 까지 허용합니다."));
        }
        if (itemV.getQuantity() == null || itemV.getQuantity() >= 9999) {
            bindingResult.addError(new FieldError("item", "quantity", itemV.getQuantity(), false, null ,null, "수량은 최대 9,999 까지 허용합니다."));
        }

        //특정 필드가 아닌 복합 룰 검증
        if (itemV.getPrice() != null && itemV.getQuantity() != null) {
            int resultPrice = itemV.getPrice() * itemV.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.addError(new ObjectError("item",null ,null, "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice));
            }
        }

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            return "validation/v2/addForm";
        }

        //성공 로직
        ItemV savedItemV = validationItemRepository.save(itemV);
        redirectAttributes.addAttribute("itemId", savedItemV.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute ItemV itemV, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        //검증 로직
        if (!StringUtils.hasText(itemV.getItemName())) {
            bindingResult.addError(new FieldError("item", "itemName", itemV.getItemName(), false, new String[]{"required.item.itemName"}, null, null));
        }
        if (itemV.getPrice() == null || itemV.getPrice() < 1000 || itemV.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item", "price", itemV.getPrice(), false, new String[]{"range.item.price"}, new Object[]{1000, 1000000}, null));
        }
        if (itemV.getQuantity() == null || itemV.getQuantity() >= 9999) {
            bindingResult.addError(new FieldError("item", "quantity", itemV.getQuantity(), false, new String[]{"max.item.quantity"} ,new Object[]{9999}, null));
        }

        //특정 필드가 아닌 복합 룰 검증
        if (itemV.getPrice() != null && itemV.getQuantity() != null) {
            int resultPrice = itemV.getPrice() * itemV.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.addError(new ObjectError("item",new String[]{"totalPriceMin"} ,new Object[]{10000, resultPrice}, null));
            }
        }

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            return "validation/v2/addForm";
        }

        //성공 로직
        ItemV savedItemV = validationItemRepository.save(itemV);
        redirectAttributes.addAttribute("itemId", savedItemV.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

//    @PostMapping("/add")
    public String addItemV4(@ModelAttribute ItemV itemV, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        log.info("objectName={}", bindingResult.getObjectName());
        log.info("target={}", bindingResult.getTarget());

        if (!StringUtils.hasText(itemV.getItemName())) {
            bindingResult.rejectValue("itemName", "required");
        }
        if (itemV.getPrice() == null || itemV.getPrice() < 1000 || itemV.getPrice() > 1000000) {
            bindingResult.rejectValue("price", "range", new Object[]{1000, 10000000}, null);
        }
        if (itemV.getQuantity() == null || itemV.getQuantity() >= 9999) {
            bindingResult.rejectValue("quantity", "max", new Object[]{9999}, null);
        }

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
            return "validation/v2/addForm";
        }

        //성공 로직
        ItemV savedItemV = validationItemRepository.save(itemV);
        redirectAttributes.addAttribute("itemId", savedItemV.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

//    @PostMapping("/add")
    public String addItemV5(@ModelAttribute ItemV itemV, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        itemValidator.validate(itemV, bindingResult);

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            return "validation/v2/addForm";
        }

        //성공 로직
        ItemV savedItemV = validationItemRepository.save(itemV);
        redirectAttributes.addAttribute("itemId", savedItemV.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    @PostMapping("/add")
    public String addItemV6(@Validated @ModelAttribute ItemV itemV, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            return "validation/v2/addForm";
        }

        //성공 로직
        ItemV savedItemV = validationItemRepository.save(itemV);
        redirectAttributes.addAttribute("itemId", savedItemV.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        ItemV itemV = validationItemRepository.findById(itemId);
        model.addAttribute("item", itemV);
        return "validation/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute ItemV itemV) {
        validationItemRepository.update(itemId, itemV);
        return "redirect:/validation/v2/items/{itemId}";
    }

}

