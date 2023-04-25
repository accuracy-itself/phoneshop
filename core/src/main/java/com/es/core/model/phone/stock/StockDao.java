package com.es.core.model.phone.stock;

import java.util.Optional;

public interface StockDao {
    Optional<Stock> get(Long phoneId);
    void save(Stock stock);
}
