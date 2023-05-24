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

    @Resource
    private QuantityChecker quantityChecker;

    @Override
    public boolean supports(Class<?> aClass) {
        return QuickOrderDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        QuickOrderDto quickOrderDto = (QuickOrderDto) o;
        List<CartItemDto> items = quickOrderDto.getItems();
        quickOrderDto.setItems(quickOrderDto.getItems());
        for (int i = 0; i < items.size(); i++) {
            String valueName = String.format("items[%d]", i);

            if(items.get(i).getId() == null && items.get(i).getQuantity() == null) {
                errors.rejectValue(valueName, "emptyPhoneField", "Field is empty.");
            } else {
                if (checkPhoneId(items.get(i).getId(), valueName, errors)) {
                    quantityChecker.checkQuantity(items.get(i), valueName, errors);
                }
            }
        }
    }

    private boolean checkPhoneId(Long phoneId, String valueName, Errors errors) {
        if(phoneId == null || !phoneDao.get(phoneId).isPresent()) {
            errors.rejectValue(valueName, "phoneNotFound", "Code not found.");
            return false;
        }

        return true;
    }
}