package com.es.core.validation;

import com.es.core.cart.CartItem;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
public class QuantityValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return CartItem.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CartItem cartItem = (CartItem) o;
        if (cartItem.getQuantity() <= 0) {
            errors.rejectValue("quantity", "notPositiveError");
        }
    }
}