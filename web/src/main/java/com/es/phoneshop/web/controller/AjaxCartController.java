package com.es.phoneshop.web.controller;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.model.phone.stock.OutOfStockException;
import com.es.phoneshop.web.dto.CartDto;
import com.es.phoneshop.web.dto.CartItemDto;
import com.es.phoneshop.web.validation.QuantityValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    @Resource
    private CartService cartService;

    @Resource
    private QuantityValidator quantityValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(quantityValidator);
    }

    @PostMapping
    public ResponseEntity<?> addPhone(@RequestBody @Valid CartItemDto cartItemDto, BindingResult bindingResult) {
        String jsonMessageTemplate = "{\"message\": \"%s\"}";
        String quantityErrorMessage = "Quantity must be a positive number.";
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(String.format(jsonMessageTemplate, quantityErrorMessage));
        }
        try {
            cartService.addPhone(cartItemDto.getId(), cartItemDto.getQuantityValue());
        } catch (OutOfStockException e) {
            return ResponseEntity.badRequest()
                    .body(String.format(jsonMessageTemplate, e.getErrorInfos().get(0).getMessage()));
        }

        Cart cart = cartService.getCart();

        return ResponseEntity.ok().body(new CartDto(cart.getTotalCost(), cart.getTotalQuantity()));
    }
}
