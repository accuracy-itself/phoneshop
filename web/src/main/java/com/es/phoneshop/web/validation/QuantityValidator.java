package com.es.phoneshop.web.validation;

import com.es.phoneshop.web.dto.CartItemDto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
public class QuantityValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return CartItemDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CartItemDto cartItemDto = (CartItemDto) o;
        String valueName = "quantityValue";
        QuantityChecker.checkQuantity(cartItemDto, valueName, errors);
    }
}