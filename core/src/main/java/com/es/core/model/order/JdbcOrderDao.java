package com.es.core.model.order;

import com.es.core.model.phone.PhoneDao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class JdbcOrderDao implements OrderDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private PhoneDao phoneDao;

    private final String QUERY_SELECT_ORDER_BY_NUMBER_ID = "select * from orders where orders.id = ? ";

    private final String QUERY_SELECT_ORDER_BY_SECURE_ID = "select * from orders where orders.secureId = ? ";

    private final String QUERY_SELECT_ORDER_ITEMS_BY_ORDER_ID = "select * from order_items where order_items.orderId = ? ";

    private final String QUERY_INSERT_ORDER_ITEMS =
            "insert into order_items (phoneId, orderId, quantity) " +
                    "values (?, ?, ?) ";

    @Override
    public Optional<Order> get(Long orderId) {
        return getOrder(QUERY_SELECT_ORDER_BY_NUMBER_ID, orderId);
    }

    @Override
    public Optional<Order> getBySecureId(String secureId) {
        return getOrder(QUERY_SELECT_ORDER_BY_SECURE_ID, secureId);
    }

    private <T> Optional<Order> getOrder(String sqlQuery, T id) {
        Order order;
        try {
            order = (Order) jdbcTemplate.queryForObject(
                    sqlQuery,
                    new BeanPropertyRowMapper(Order.class),
                    id);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

        List<OrderItem> orderItems = jdbcTemplate.query(
                QUERY_SELECT_ORDER_ITEMS_BY_ORDER_ID,
                new Object[]{order.getId()}, (rs, rowNum) ->
                        new OrderItem(
                                phoneDao.get(rs.getLong("phoneId")).get(),
                                order,
                                rs.getInt("quantity")
                        ));

        order.setItems(orderItems);

        return Optional.of(order);
    }

    @Override
    public void save(Order order) {
        String secureId = UUID.randomUUID().toString();
        order.setSecureId(secureId);
        order.setStatus(OrderStatus.NEW);
        SimpleJdbcInsert orderSimpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        Map<String, Object> orderParameters = new HashMap<>();
        orderParameters.put("secureId", order.getSecureId());
        orderParameters.put("subtotal", order.getSubtotal());
        orderParameters.put("deliveryPrice", order.getDeliveryPrice());
        orderParameters.put("totalPrice", order.getTotalPrice());
        orderParameters.put("firstName", order.getFirstName());
        orderParameters.put("lastName", order.getLastName());
        orderParameters.put("deliveryAddress", order.getDeliveryAddress());
        orderParameters.put("contactPhoneNo", order.getContactPhoneNo());
        //maybe it would be better to create status table in schema and to store here status id
        orderParameters.put("status", order.getStatus().toString());

        KeyHolder keyHolder = orderSimpleJdbcInsert
                .withTableName("orders")
                .usingColumns("secureId", "subtotal", "deliveryPrice", "totalPrice",
                        "firstName", "lastName", "deliveryAddress", "contactPhoneNo", "status")
                .usingGeneratedKeyColumns("id")
                .withoutTableColumnMetaDataAccess()
                .executeAndReturnKeyHolder(orderParameters);

        int orderId = keyHolder.getKey().intValue();

        jdbcTemplate.batchUpdate(QUERY_INSERT_ORDER_ITEMS,
                order.getItems(),
                10,
                (PreparedStatement ps, OrderItem orderItem) -> {
                    ps.setLong(1, orderItem.getPhone().getId());
                    ps.setLong(2, orderId);
                    ps.setInt(3, orderItem.getQuantity());
                });
    }
}
