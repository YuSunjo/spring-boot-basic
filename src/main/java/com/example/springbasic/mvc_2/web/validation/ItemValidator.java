package com.example.springbasic.mvc_2.web.validation;

import com.example.springbasic.mvc_2.domain.validation.ItemV;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ItemValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ItemV.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ItemV itemV = (ItemV) target;

        if (!StringUtils.hasText(itemV.getItemName())) {
            errors.rejectValue("itemName", "required");
        }
        if (itemV.getPrice() == null || itemV.getPrice() < 1000 || itemV.getPrice() > 1000000) {
            errors.rejectValue("price", "range", new Object[]{1000, 10000000}, null);
        }
        if (itemV.getQuantity() == null || itemV.getQuantity() >= 9999) {
            errors.rejectValue("quantity", "max", new Object[]{9999}, null);
        }

        //특정 필드가 아닌 복합 룰 검증
        if (itemV.getPrice() != null && itemV.getQuantity() != null) {
            int resultPrice = itemV.getPrice() * itemV.getQuantity();
            if (resultPrice < 10000) {
                errors.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }
    }
}
