package com.es.phoneshop.web.dto.converter;

import com.es.core.cart.Cart;
import com.es.phoneshop.web.dto.CartDto;
import com.es.phoneshop.web.dto.CartItemDto;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

public class CartDtoConverter implements DtoConverter<CartDto, Cart> {
    @Resource
    private CartItemDtoConverter cartItemDtoConverter;

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
