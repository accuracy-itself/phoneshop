package com.es.core.model.order;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    Optional<Order> get(Long key);

    Optional<Order> getBySecureId(String key);
    void save(Order order);
    List<Order> getAllOrders();
    void updateStatus(Long orderId, OrderStatus newStatus);
}
