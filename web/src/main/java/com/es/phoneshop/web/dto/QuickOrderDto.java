package com.es.phoneshop.web.dto;

import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
public class QuickOrderDto {
    private int capacity = 8;

    @Valid
    private List<CartItemDto> items;
}
