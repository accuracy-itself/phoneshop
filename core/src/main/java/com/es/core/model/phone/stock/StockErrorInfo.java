package com.es.core.model.phone.stock;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class StockErrorInfo {
    private Long phoneId;
    private Integer stockRequested;
    private Integer stockAvailable;
    private String message;

    public StockErrorInfo(Long phoneId, Integer stockRequested, Integer stockAvailable){
        this.phoneId = phoneId;
        this.stockRequested = stockRequested;
        this.stockAvailable = stockAvailable;
        this.message = "Out of stock, available: " + this.stockAvailable.toString() + ".";
    }
}
