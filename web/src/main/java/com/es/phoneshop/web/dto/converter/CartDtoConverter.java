package com.es.phoneshop.web.dto.converter;

import com.es.core.cart.Cart;
import com.es.phoneshop.web.dto.CartDto;
import com.es.phoneshop.web.dto.CartItemDto;

import java.util.List;
import java.util.stream.Collectors;

public class CartDtoConverter implements DtoConverter<CartDto, Cart> {
    private CartItemDtoConverter cartItemDtoConverter;

    public CartDtoConverter() {
        this.cartItemDtoConverter = new CartItemDtoConverter();
    }

    @Override
    public CartDto convertToDto(Cart cart) {
        List<CartItemDto> cartDtoItems = cart.getItems().stream()
                .map(cartItem -> cartItemDtoConverter.convertToDto(cartItem))
                .collect(Collectors.toList());
        return new CartDto(cart.getTotalCost(),
                cart.getTotalQuantity(),
                cartDtoItems);
    }

    @Override
    public Cart convertToModel(CartDto cartDto) {
        return null;
    }
}
