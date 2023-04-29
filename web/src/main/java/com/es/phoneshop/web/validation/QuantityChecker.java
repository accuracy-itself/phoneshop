package com.es.phoneshop.web.validation;

import com.es.phoneshop.web.dto.CartItemDto;
import org.springframework.validation.Errors;

public class QuantityChecker {
    public static void checkQuantity(CartItemDto cartItemDto, String valueName, Errors errors) {
        if (cartItemDto.getQuantity() == null) {
            errors.rejectValue(valueName, "nullValue", "Quantity must be presented");
        } else {
            try {
                int quantity = Integer.parseInt(cartItemDto.getQuantity());
                if (quantity <= 0) {
                    errors.rejectValue(valueName, "notPositive", "Quantity must be positive");
                } else {
                    cartItemDto.setQuantityValue(quantity);
                }
            } catch (NumberFormatException ex) {
                errors.rejectValue(valueName, "notNumber", "Quantity must be a number");
            }
        }
    }
}
