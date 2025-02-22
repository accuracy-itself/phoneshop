package com.es.core.cart;

import com.es.core.model.phone.stock.OutOfStockException;

import java.util.Map;

public interface CartService {

    Cart getCart();

    void addPhone(Long phoneId, Integer quantity) throws OutOfStockException;

    /**
     * @param items
     * key: {@link com.es.core.model.phone.Phone#id}
     * value: quantity
     */
    void update(Map<Long, Integer> items) throws OutOfStockException;

    void remove(Long phoneId);

    void recalculateCart(Cart cart);

    void clearCart();
}
