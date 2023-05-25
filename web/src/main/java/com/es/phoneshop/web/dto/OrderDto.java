package com.es.phoneshop.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private BigDecimal subtotal;
    private BigDecimal deliveryPrice;
    private BigDecimal totalPrice;

    @Pattern(regexp = "[a-zA-Z]+")
    private String firstName;
    @Pattern(regexp = "^(?=\\s*\\S).*$")
    private String lastName;
    @Pattern(regexp = "^(?=\\s*\\S).*$")
    private String deliveryAddress;
    @Pattern(regexp = "[0-9]+")
    private String contactPhoneNo;

    private List<OrderItemDto> items;

    public OrderDto(BigDecimal subtotal, BigDecimal deliveryPrice, BigDecimal totalPrice) {
        this.subtotal = subtotal;
        this.deliveryPrice = deliveryPrice;
        this.totalPrice = totalPrice;
    }
}
