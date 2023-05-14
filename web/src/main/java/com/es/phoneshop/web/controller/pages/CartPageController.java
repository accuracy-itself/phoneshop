package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.core.model.phone.stock.OutOfStockException;
import com.es.phoneshop.web.dto.CartDto;
import com.es.phoneshop.web.dto.CartItemDto;
import com.es.phoneshop.web.dto.converter.CartDtoConverter;
import com.es.phoneshop.web.validation.QuantityUpdateValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {
    @Resource
    private CartService cartService;

    @Resource
    private QuantityUpdateValidator quantityUpdateValidator;

    @Resource
    private CartDtoConverter cartDtoConverter;

    @ModelAttribute
    public CartDto setCartDto() {
        return cartDtoConverter.convertToDto(cartService.getCart());
    }

    @GetMapping
    public String getCart(Model model) {
        model.addAttribute("cart", cartService.getCart());
        return "cart";
    }

    @PostMapping
    public String updateCart(@ModelAttribute("cartDto") @Valid CartDto cartDto,
                             BindingResult bindingResult,
                             Model model) {
        quantityUpdateValidator.validate(cartDto, bindingResult);
        if (!bindingResult.hasErrors()) {
            try {
                cartService.update(cartDto.getItems().stream()
                        .collect(Collectors.toMap(CartItemDto::getId, CartItemDto::getQuantityValue)));
            } catch (OutOfStockException e) {
                Map<Long, String> errors = new HashMap<>();
                errors.put(e.getErrorInfos().get(0).getPhoneId(), e.getErrorInfos().get(0).getMessage());
                model.addAttribute("error_quantities", errors);
            }
        }
        model.addAttribute("cart", cartService.getCart());
        return "cart";
    }

    @PostMapping(value = "/delete/{phoneId}")
    public String deleteItem(@PathVariable Long phoneId, Model model) {
        cartService.remove(phoneId);
        model.addAttribute("cart", cartService.getCart());
        return "redirect:/cart";
    }
}
