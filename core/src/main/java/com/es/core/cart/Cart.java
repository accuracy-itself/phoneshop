package com.es.core.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Cart implements Serializable {
    private List<CartItem> items;
    private BigDecimal totalCost;
    private Integer totalQuantity;

    public Cart() {
        this.items = new ArrayList<>();
        this.totalQuantity = 0;
        this.totalCost = new BigDecimal(0);
    }
}
