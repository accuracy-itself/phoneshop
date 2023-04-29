package com.es.core.cart;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import com.es.core.model.phone.Phone;

@Data
@NoArgsConstructor
public class CartItem implements Serializable {
    private Phone phone;
    private Integer quantity;

    public CartItem(Phone phone, Integer quantity) {
        this.phone = phone;
        this.quantity = quantity;
    }
}
