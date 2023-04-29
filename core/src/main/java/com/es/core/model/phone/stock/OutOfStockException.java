package com.es.core.model.phone.stock;

public class OutOfStockException extends Exception {
    private Long phoneId;
    private Integer stockRequested;
    private Integer stockAvailable;

    private String message;

    public OutOfStockException(Long phoneId, int stockRequested, int stockAvailable) {
        this.phoneId = phoneId;
        this.stockRequested = stockRequested;
        this.stockAvailable = stockAvailable;
        this.message = "Out of stock, available: " + this.stockAvailable.toString() + ".";
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public int getStockRequested() {
        return stockRequested;
    }

    public int getStockAvailable() {
        return stockAvailable;
    }

    @Override
    public String getMessage() {
        return message;
    }
}