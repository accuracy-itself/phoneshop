package com.es.phoneshop.web.validation;

import com.es.core.model.phone.PhoneDao;
import com.es.phoneshop.web.dto.CartItemDto;
import com.es.phoneshop.web.dto.QuickOrderDto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;
import java.util.List;

public class QuickOrderValidator implements Validator {
    @Resource
    private PhoneDao phoneDao;

    @Override
    public boolean supports(Class<?> aClass) {
        return QuickOrderDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        QuickOrderDto quickOrderDto = (QuickOrderDto) o;
        List<CartItemDto> items = quickOrderDto.getItems();

        for (int i = 0; i < items.size(); i++) {
            String idValueName = String.format("items[%d].id", i);
            if (!phoneDao.get(items.get(i).getId()).isPresent()) {
                errors.rejectValue(idValueName, "phoneNotFound", "");
            }
        }
    }
}