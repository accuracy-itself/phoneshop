package com.es.phoneshop.web.controller;

import com.es.core.cart.Cart;
import com.es.core.cart.CartInfo;
import com.es.core.cart.CartItem;
import com.es.core.cart.CartService;
import com.es.core.validation.QuantityValidator;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
    @ResponseBody
    public CartInfo addPhone(@RequestBody @Valid CartItem cartItem,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return null;
        }
        try {
            cartService.addPhone(cartItem.getId(), cartItem.getQuantity());
        } catch (Exception e) {
            return null;
        }

        Cart cart = cartService.getCart();

        return new CartInfo(cart.getTotalCost(), cart.getTotalQuantity());
    }
}
