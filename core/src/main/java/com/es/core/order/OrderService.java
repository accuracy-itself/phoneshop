package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.model.phone.stock.OutOfStockException;

import java.util.Optional;

public interface OrderService {
    Order createOrder(Cart cart);
    void placeOrder(Order order) throws OutOfStockException;
    Optional<Order> getOrderBySecureId(String secureId);
}
