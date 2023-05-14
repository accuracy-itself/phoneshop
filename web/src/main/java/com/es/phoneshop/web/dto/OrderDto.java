package com.es.phoneshop.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private BigDecimal subtotal;
    private BigDecimal deliveryPrice;
    private BigDecimal totalPrice;

    private String firstName;

    private String lastName;

    private String deliveryAddress;

    private String contactPhoneNo;

    private List<OrderItemDto> items;

    public OrderDto(BigDecimal subtotal, BigDecimal deliveryPrice, BigDecimal totalPrice) {
        this.subtotal = subtotal;
        this.deliveryPrice = deliveryPrice;
        this.totalPrice = totalPrice;
    }
}
