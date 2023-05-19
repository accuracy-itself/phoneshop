package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.core.model.phone.PhoneDao;
import com.es.core.model.phone.stock.OutOfStockException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/quickOrderEntry")
public class QuickOrderEntryPageController {
    @Resource
    private CartService cartService;

    @Resource
    private PhoneDao phoneDao;

    private final int capacity = 8;

    @GetMapping
    public String getQuickOrder(Model model) {
        model.addAttribute("capacity", capacity);
        model.addAttribute("cart", cartService.getCart());
        return "quickOrderEntry";
    }

    @PostMapping
    public String updateCart(@RequestParam("phoneCode") String[] codes,
                             @RequestParam("phoneQuantity") String[] quantities,
                             Model model) {

        Map<Integer, String> errors = new HashMap<>();
        List<String> successes = new ArrayList<>();
        model.addAttribute("capacity", capacity);

        for (int i = 0; i < codes.length; i++) {
            if (!codes[i].trim().isEmpty()) {
                try {
                    long codeValue = Long.parseLong(codes[i]);
                    if (!phoneDao.get((long) codeValue).isPresent()) {
                        errors.put(i, codes[i] + " not found.");
                    } else {

                        if (!quantities[i].trim().isEmpty()) {
                            try {
                                int quantityValue = Integer.parseInt(quantities[i]);
                                if (quantityValue <= 0) {
                                    errors.put(i, quantities[i] + " not a valid quantity.");
                                } else {
                                    try {
                                        cartService.addPhone(codeValue, quantityValue);
                                        successes.add(codes[i] + " added successfully.");
                                    } catch (OutOfStockException e) {
                                        errors.put(i, e.getErrorInfos().get(0).getMessage());
                                    }
                                }
                            } catch (NumberFormatException ex) {
                                errors.put(i, quantities[i] + " not a valid quantity.");
                            }
                        }

                    }
                } catch (NumberFormatException ex) {
                    errors.put(i, codes[i] + " not a product code.");
                }
            }
        }

        model.addAttribute("errors", errors);
        model.addAttribute("successes", successes);
        model.addAttribute("codes", codes);
        model.addAttribute("quantities", quantities);
        model.addAttribute("cart", cartService.getCart());
        return "quickOrderEntry";
    }
}
