package com.es.phoneshop.web.validation;

import com.es.phoneshop.web.dto.CartItemDto;
import com.es.phoneshop.web.dto.OrderDto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class OrderDtoValidator implements Validator {
    private final String errorMessage = "This value is required.";

    @Override
    public boolean supports(Class<?> aClass) {
        return CartItemDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        OrderDto orderDto = (OrderDto) o;
        checkValue(orderDto.getFirstName(), "firstName", errors);
        checkValue(orderDto.getLastName(), "lastName", errors);
        checkValue(orderDto.getContactPhoneNo(), "contactPhoneNo", errors);
        checkValue(orderDto.getDeliveryAddress(), "deliveryAddress", errors);
    }

    private void checkValue(String value, String name, Errors errors) {
        if (value == null || value.trim().equals("")) {
            errors.rejectValue(name, "nullValue", errorMessage);
        }
    }
}