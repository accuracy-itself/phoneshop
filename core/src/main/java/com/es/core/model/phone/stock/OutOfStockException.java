package com.es.core.model.phone.stock;

import java.util.List;

public class OutOfStockException extends Exception {
    private List<StockErrorInfo> errorInfos;

    public OutOfStockException(List<StockErrorInfo> errorInfos) {
        this.errorInfos = errorInfos;
    }

    public List<StockErrorInfo> getErrorInfos() {
        return errorInfos;
    }
}