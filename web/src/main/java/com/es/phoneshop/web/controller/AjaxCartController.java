package com.es.phoneshop.web.controller;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.model.phone.stock.OutOfStockException;
import com.es.phoneshop.web.dto.CartDto;
import com.es.phoneshop.web.dto.CartItemDto;
import com.es.phoneshop.web.dto.MessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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

    private final String QUANTITY_ERROR_MESSAGE = "Quantity must be a positive number.";


    @PostMapping
    public ResponseEntity<?> addPhone(@RequestBody @Valid CartItemDto cartItemDto,
                                      BindingResult bindingResult) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        if (bindingResult.hasErrors()) {
            MessageDto messageDto = new MessageDto(QUANTITY_ERROR_MESSAGE);
            return ResponseEntity.badRequest()
                    .body(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(messageDto));
        }
        try {
            cartService.addPhone(cartItemDto.getId(), cartItemDto.getQuantity());
        } catch (OutOfStockException e) {
            MessageDto messageDto = new MessageDto(e.getErrorInfos().get(0).getMessage());
            return ResponseEntity.badRequest()
                    .body(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(messageDto));
        }

        Cart cart = cartService.getCart();

        return ResponseEntity.ok().body(new CartDto(cart.getTotalCost(), cart.getTotalQuantity()));
    }
}
