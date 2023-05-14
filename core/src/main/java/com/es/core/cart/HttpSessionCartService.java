package com.es.core.cart;

import com.es.core.model.phone.JdbcPhoneDao;
import com.es.core.model.phone.stock.JdbcStockDao;
import com.es.core.model.phone.stock.OutOfStockException;
import com.es.core.model.phone.stock.Stock;
import com.es.core.model.phone.stock.StockErrorInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HttpSessionCartService implements CartService {
    private final Cart cart;
    private final JdbcPhoneDao phoneDao;
    private final JdbcStockDao stockDao;


    @Override
    public synchronized Cart getCart() {
        return cart;
    }

    @Override
    public void addPhone(Long phoneId, Integer quantity) throws OutOfStockException {
        List<CartItem> items = cart.getItems();
        Optional<CartItem> cartItem = items.stream()
                .filter(item -> item.getPhone().getId().equals(phoneId))
                .findAny();

        if (cartItem.isPresent()) {
            quantity += cartItem.get().getQuantity();
        }

        Integer stockAvailable = stockDao.get(phoneId).get().getStock() - stockDao.get(phoneId).get().getReserved();
        if (quantity > stockAvailable) {
            StockErrorInfo errorInfo = new StockErrorInfo(phoneId, quantity, stockAvailable);
            List<StockErrorInfo> errorInfos = new ArrayList<>();
            errorInfos.add(errorInfo);
            throw new OutOfStockException(errorInfos);
        } else {
            if (cartItem.isPresent()) {
                cartItem.get().setQuantity(quantity);
            } else {
                items.add(new CartItem(phoneDao.get(phoneId).get(), quantity));
            }
        }

        recalculateCart(cart);
    }

    @Override
    public void update(Map<Long, Integer> items) throws OutOfStockException {
        List<CartItem> cartItems = cart.getItems();
        for (Map.Entry<Long, Integer> item : items.entrySet()) {
            Integer quantity = item.getValue();
            Long id = item.getKey();
            Stock stock = stockDao.get(id).get();
            Integer stockAvailable = stock.getStock() - stock.getReserved();

            if (quantity > stockAvailable) {
                StockErrorInfo errorInfo = new StockErrorInfo(id, quantity, stockAvailable);
                List<StockErrorInfo> errorInfos = new ArrayList<>();
                errorInfos.add(errorInfo);
                throw new OutOfStockException(errorInfos);
            }
            cartItems.stream()
                    .filter(cartItem -> cartItem.getPhone().getId().equals(id))
                    .findAny()
                    .ifPresent(cartItem -> cartItem.setQuantity(quantity));
        }

        recalculateCart(cart);
    }

    @Override
    public void remove(Long phoneId) {
        cart.getItems().removeIf(item ->
                item.getPhone().getId().equals(phoneId));
        recalculateCart(cart);
    }

    @Override
    public synchronized void recalculateCart(Cart cart) {
        cart.setTotalQuantity(cart.getItems().stream()
                .map(CartItem::getQuantity).mapToInt(Integer::intValue).sum()
        );

        cart.setTotalCost(cart.getItems().stream()
                .map(item -> item.getPhone().getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
    }

    @Override
    public void clearCart() {
        cart.setItems(new ArrayList<>());
        cart.setTotalCost(new BigDecimal(0));
        cart.setTotalQuantity(0);
    }
}
