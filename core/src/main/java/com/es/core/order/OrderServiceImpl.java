package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.model.order.JdbcOrderDao;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.stock.JdbcStockDao;
import com.es.core.model.phone.stock.OutOfStockException;
import com.es.core.model.phone.stock.Stock;
import com.es.core.model.phone.stock.StockErrorInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    JdbcOrderDao orderDao;

    @Resource
    JdbcStockDao stockDao;

    @Value("${delivery.price}")
    private BigDecimal deliveryPrice;

    @Override
    public Order createOrder(Cart cart) {
        Order order = new Order();

        List<OrderItem> items = cart.getItems().stream()
                .map(cartItem -> new OrderItem(cartItem.getPhone(), order, cartItem.getQuantity()))
                .collect(Collectors.toList());
        return new Order(items, cart.getTotalCost(), deliveryPrice);
    }

    @Override
    @Transactional
    public void placeOrder(Order order) throws OutOfStockException {
        checkStocks(order.getItems());

        order.getItems().forEach(item -> {
            int oldReserved = stockDao.get(item.getPhone().getId()).get().getReserved();
            int newReserved = item.getQuantity() + oldReserved;
            stockDao.update(item.getPhone().getId(), newReserved);
        });
        orderDao.save(order);

    }

    private void checkStocks(List<OrderItem> items) throws OutOfStockException {
        List<StockErrorInfo> errorInfos = new ArrayList<>();
        items.forEach(item -> {
            Long phoneId = item.getPhone().getId();
            Stock stock = stockDao.get(phoneId).get();
            int stockAvailable = stock.getStock() - stock.getReserved();

            if (stockAvailable == 0) {
                StockErrorInfo errorInfo = new StockErrorInfo(phoneId, 0, stockAvailable);
                errorInfos.add(errorInfo);
            }
        });

        if (!errorInfos.isEmpty()) {
            throw new OutOfStockException(errorInfos);
        }
    }

    @Override
    public Optional<Order> getOrderBySecureId(String secureId) {
        return orderDao.getBySecureId(secureId);
    }
}
