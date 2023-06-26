package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.core.model.phone.stock.OutOfStockException;
import com.es.phoneshop.web.dto.CartDto;
import com.es.phoneshop.web.dto.CartItemDto;
import com.es.phoneshop.web.dto.converter.CartDtoConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {
    @Resource
    private CartService cartService;

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
        if (!bindingResult.hasErrors()) {
            try {
                cartService.update(cartDto.getItems().stream()
                        .collect(Collectors.toMap(CartItemDto::getId, CartItemDto::getQuantity)));
            } catch (OutOfStockException e) {
                long errorId = e.getErrorInfos().get(0).getPhoneId();
                int errorIndex = IntStream.range(0, cartDto.getItems().size())
                        .filter(index -> cartDto.getItems().get(index).getId().equals(errorId))
                        .findFirst()
                        .orElse(-1);
                String valueName = String.format("items[%d].quantity", errorIndex);
                bindingResult.addError(new FieldError("cartDto", valueName,
                        e.getErrorInfos().get(0).getStockRequested(),
                        false, null, null, e.getErrorInfos().get(0).getMessage()));
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
