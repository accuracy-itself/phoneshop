package com.es.phoneshop.web.controller;

import com.es.core.cart.Cart;
import com.es.core.cart.CartInfo;
import com.es.core.cart.CartItem;
import com.es.core.cart.CartService;
import com.es.core.model.phone.stock.OutOfStockException;
import com.es.core.validation.QuantityValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;


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

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addPhone(@RequestBody @Valid CartItem cartItem, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body("Quantity must be a positive number.");
        }
        try {
            cartService.addPhone(cartItem.getId(), cartItem.getQuantity());
        } catch (OutOfStockException e) {
            return ResponseEntity.badRequest()
                    .body("Out of stock, available: " + e.getStockAvailable() + ".");
        }

        Cart cart = cartService.getCart();

        return ResponseEntity.ok().body(new CartInfo(cart.getTotalCost(), cart.getTotalQuantity()));
    }
}
