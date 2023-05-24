package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.core.model.phone.stock.OutOfStockException;
import com.es.phoneshop.web.dto.CartItemDto;
import com.es.phoneshop.web.dto.QuickOrderDto;
import com.es.phoneshop.web.validation.QuickOrderValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/quickOrderEntry")
public class QuickOrderEntryPageController {
    @Resource
    private CartService cartService;

    @Resource
    private QuickOrderValidator quickOrderValidator;


    @GetMapping
    public ModelAndView getQuickOrder(Model model) {
        model.addAttribute("cart", cartService.getCart());
        return new ModelAndView("quickOrderEntry", "quickOrderDto", new QuickOrderDto());
    }

    @PostMapping
    public String updateCart(@ModelAttribute("quickOrderDto") @Valid QuickOrderDto quickOrderDto,
                             BindingResult bindingResult,
                             Model model) {
        quickOrderValidator.validate(quickOrderDto, bindingResult);

        List<String> successes = new ArrayList<>();
        List<CartItemDto> items = quickOrderDto.getItems();

        for (int i = 0; i < items.size(); i++) {
            String valueName = String.format("items[%d]", i);
            CartItemDto item = items.get(i);
            if (!(bindingResult.hasFieldErrors(valueName))) {
                try {
                    cartService.addPhone(item.getId(), item.getQuantity());
                    successes.add(item.getId() + " added successfully.");
                    item.setId(null);
                    item.setQuantity(null);
                } catch (OutOfStockException e) {
                    String quantityValueName = String.format("items[%d].quantity", i);
                    bindingResult.addError(new FieldError("cartDto", quantityValueName,
                            e.getErrorInfos().get(0).getStockRequested(),
                            false, null, null, e.getErrorInfos().get(0).getMessage()));
                }
            }
        }

        model.addAttribute("cart", cartService.getCart());
        model.addAttribute("successes", successes);
        return "quickOrderEntry";
    }
}