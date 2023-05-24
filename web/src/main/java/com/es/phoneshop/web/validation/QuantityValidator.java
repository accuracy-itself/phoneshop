package com.es.phoneshop.web.validation;

import com.es.phoneshop.web.dto.CartItemDto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import javax.annotation.Resource;

public class QuantityValidator implements Validator {
    @Resource
    QuantityChecker quantityChecker;

    @Override
    public boolean supports(Class<?> aClass) {
        return CartItemDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CartItemDto cartItemDto = (CartItemDto) o;
        String valueName = "quantity";
        quantityChecker.checkQuantity(cartItemDto, valueName, errors);
    }
}