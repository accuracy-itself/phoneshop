package com.es.core.cart;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CartInfo {
    private BigDecimal totalCost;
    private Integer totalQuantity;
}
