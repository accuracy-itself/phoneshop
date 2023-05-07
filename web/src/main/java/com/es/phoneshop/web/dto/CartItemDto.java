package com.es.phoneshop.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto implements Serializable {
    private Long id;
    private String quantity;
    private Integer quantityValue;
}
