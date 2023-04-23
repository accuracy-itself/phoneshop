package com.es.core.cart;

import com.es.core.model.phone.JdbcPhoneDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HttpSessionCartService implements CartService {
    private final Cart cart;
    private final JdbcPhoneDao phoneDao;

    @Override
    public synchronized Cart getCart() {
        return cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) {
        List<CartItem> items = cart.getItems();
        Optional<CartItem> cartItem = items.stream()
                .filter(item -> item.getId().equals(phoneId))
                .findAny();

        if (cartItem.isPresent()) {
            quantity += cartItem.get().getQuantity();
        }

        if (quantity > 100) {
            //throw new OutOfStockException(product, quantity, product.getStock());
        } else {
            if (cartItem.isPresent()) {
                cartItem.get().setQuantity(quantity);
            } else {
                items.add(new CartItem(phoneId, quantity));
            }
        }

        recalculateCart(cart);
    }

    @Override
    public void update(Map<Long, Long> items) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void remove(Long phoneId) {
        throw new UnsupportedOperationException("TODO");
    }

    private synchronized void recalculateCart(Cart cart) {
        cart.setTotalQuantity(cart.getItems().stream()
                .map(CartItem::getQuantity).mapToLong(Long::longValue).sum()
        );

        cart.setTotalCost(cart.getItems().stream()
                .map(item -> phoneDao.get(item.getId()).get().getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
    }
}
