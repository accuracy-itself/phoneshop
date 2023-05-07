package com.es.phoneshop.web.dto.converter;

import com.es.core.cart.CartItem;
import com.es.phoneshop.web.dto.CartItemDto;

public class CartItemDtoConverter implements DtoConverter<CartItemDto, CartItem> {
    @Override
    public CartItemDto convertToDto(CartItem cartItem) {
        return new CartItemDto(cartItem.getPhone().getId(),
                cartItem.getQuantity().toString(),
                cartItem.getQuantity());
    }

    @Override
    public CartItem convertToModel(CartItemDto cartItemDto) {
        return null;
    }
}
