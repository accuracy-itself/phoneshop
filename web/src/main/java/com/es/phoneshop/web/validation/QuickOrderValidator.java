package com.es.phoneshop.web.validation;

import com.es.core.model.phone.PhoneDao;
import com.es.phoneshop.web.dto.CartItemDto;
import com.es.phoneshop.web.dto.QuickOrderDto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        for (int i = 0; i < items.size(); i++) {
            String valueName = String.format("items[%d]", i);
            String idValueName = String.format("items[%d].id", i);
            String quantityValueName = String.format("items[%d].quantity", i);

            if(items.get(i).getId() != null || items.get(i).getQuantity() != null) {
                if (checkPhoneId(items.get(i).getId(), idValueName, errors)) {
                    quantityChecker.checkQuantity(items.get(i), quantityValueName, errors);
                }
            }

            Map<String, Object> valuesInfo = new HashMap<>();
            valuesInfo.put(idValueName, items.get(i).getId());
            valuesInfo.put(quantityValueName, items.get(i).getQuantity());

            checkPhoneField(valueName, valuesInfo, errors);
        }
    }

    private boolean checkPhoneId(Long phoneId, String valueName, Errors errors) {
        if (phoneId == null || !phoneDao.get(phoneId).isPresent()) {
            errors.rejectValue(valueName, "phoneNotFound", "Code not found.");
            return false;
        }

        return true;
    }

    private void checkPhoneField(String fieldName, Map<String, Object> valuesInfo, Errors errors) {
        boolean hasErrors = false;
        for (Map.Entry<String, Object> entry : valuesInfo.entrySet()) {
            if(entry.getValue() == null || errors.hasFieldErrors(entry.getKey())) {
                hasErrors = true;
            }
        }

        if(hasErrors) {
            errors.rejectValue(fieldName, "empty.field", "");
        }
    }
}