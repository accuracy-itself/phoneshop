package com.es.phoneshop.web.validation;

import com.es.phoneshop.web.dto.CartDto;
import com.es.phoneshop.web.dto.CartItemDto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

public class QuantityUpdateValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return CartDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CartDto cartDto = (CartDto) o;
        List<CartItemDto> cartDtoItems = cartDto.getItems();
        for (int i = 0; i < cartDtoItems.size(); i++) {
            CartItemDto cartItemDto = cartDtoItems.get(i);
            String valueName = String.format("items[%d].quantity", i);
            QuantityChecker.checkQuantity(cartItemDto, valueName, errors);
        }
    }
}
