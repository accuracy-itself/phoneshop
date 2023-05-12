package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderDao;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.stock.OutOfStockException;
import com.es.core.model.phone.stock.Stock;
import com.es.core.model.phone.stock.StockDao;
import com.es.core.model.phone.stock.StockErrorInfo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class OrderServiceImpl implements OrderService {
    @Resource
    OrderDao orderDao;

    @Resource
    StockDao stockDao;

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

            if (stockAvailable < item.getQuantity()) {
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

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderDao.get(id);
    }

    @Override
    public void updateStatus(Long orderId, OrderStatus newStatus) {
        orderDao.updateStatus(orderId, newStatus);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderDao.getAllOrders();
    }

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

}
