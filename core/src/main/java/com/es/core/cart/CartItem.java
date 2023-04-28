package com.es.core.cart;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class CartItem implements Serializable {
    private Long id;

    private Integer quantity;

    public CartItem(Long id, Integer quantity) {
        this.id = id;
        this.quantity = quantity;
    }
}
