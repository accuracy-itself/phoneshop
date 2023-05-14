package com.es.core.model.order;

import com.es.core.model.phone.Phone;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    private Long id;
    private Phone phone;
    private Order order;
    private Integer quantity;

    public OrderItem(Phone phone, Order order, Integer quantity) {
        this.phone = phone;
        this.order = order;
        this.quantity = quantity;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(final Phone phone) {
        this.phone = phone;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(final Order order) {
        this.order = order;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }
}
