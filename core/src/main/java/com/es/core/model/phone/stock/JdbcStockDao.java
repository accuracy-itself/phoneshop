package com.es.core.model.phone.stock;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JdbcStockDao implements StockDao{
    @Resource
    private JdbcTemplate jdbcTemplate;

    private final String QUERY_SELECT_STOCK = "select * from stocks where stocks.phoneId = ? ";

    private final String QUERY_UPDATE_STOCK = "update stocks set reserved = ? where phoneId = ? ";

    @Override
    public Optional<Stock> get(Long phoneId) {
        Stock stock = (Stock)jdbcTemplate.queryForObject(QUERY_SELECT_STOCK, new BeanPropertyRowMapper(Stock.class), phoneId);
        return Optional.of(stock);
    }

    @Override
    public void save(Stock stock) {
        SimpleJdbcInsert stockSimpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("stocks");
        Map<String, Object> stockParameters = new HashMap<>();
        stockParameters.put("stock", stock.getStock());
        stockParameters.put("phoneId", stock.getPhone());
        stockParameters.put("reserved", stock.getReserved());
        stockSimpleJdbcInsert.execute(stockParameters);
    }

    @Override
    public void update(Long phoneId, int reserved) {
        jdbcTemplate.update(QUERY_UPDATE_STOCK, reserved, phoneId);
    }
}
