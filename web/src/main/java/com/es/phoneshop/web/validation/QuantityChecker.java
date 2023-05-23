package com.es.phoneshop.web.validation;

import com.es.phoneshop.web.dto.CartItemDto;
import org.springframework.validation.Errors;

public class QuantityChecker {
    public static void checkQuantity(CartItemDto cartItemDto, String valueName, Errors errors) {
        if (cartItemDto.getQuantity() == null) {
            errors.rejectValue(valueName, "nullValue", "Quantity must be presented");
        } else if (cartItemDto.getQuantity() <= 0) {
            errors.rejectValue(valueName, "notPositive", "Quantity must be positive");
        }
    }
}
