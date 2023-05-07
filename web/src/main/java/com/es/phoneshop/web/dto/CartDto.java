package com.es.phoneshop.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class CartDto {
    private BigDecimal totalCost;
    private Integer totalQuantity;
    @Valid
    private List<CartItemDto> items;

    public CartDto(BigDecimal totalCost, Integer totalQuantity) {
        this.totalCost = totalCost;
        this.totalQuantity = totalQuantity;        
    }
}
