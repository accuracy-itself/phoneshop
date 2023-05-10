package com.es.core.test.order;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.cart.HttpSessionCartService;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderDao;
import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.JdbcPhoneDao;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.stock.JdbcStockDao;
import com.es.core.model.phone.stock.OutOfStockException;
import com.es.core.model.phone.stock.Stock;
import com.es.core.order.OrderService;
import com.es.core.order.OrderServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@ContextConfiguration("classpath:context/applicationContext-test.xml")
@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplMockTest {
    private Cart cart = new Cart();
    private JdbcPhoneDao phoneDao = mock(JdbcPhoneDao.class);
    private JdbcStockDao stockDao = mock(JdbcStockDao.class);
    private OrderDao orderDao = mock(OrderDao.class);

    BigDecimal deliveryPrice = new BigDecimal(100);

    private OrderServiceImpl orderService = new OrderServiceImpl(orderDao, stockDao, deliveryPrice);
    private CartService cartService = new HttpSessionCartService(cart, phoneDao, stockDao);

    @Mock
    Phone testPhoneFirst;
    Long phoneIdFirst = 1003L;
    Integer quantityFirst = 5;
    Stock stockFirst = new Stock(testPhoneFirst, 20, 2);
    BigDecimal priceFirst = new BigDecimal(200);
        
    @Mock
    Phone testPhoneSecond;
    Long phoneIdSecond = 1006L;
    Integer quantitySecond = 6;
    Stock stockSecond = new Stock(testPhoneSecond, 20, 2);
    BigDecimal priceSecond = new BigDecimal(300);

    Integer cartSize = 2;

    @Before
    public void setup() throws OutOfStockException {
        when(testPhoneFirst.getId()).thenReturn(phoneIdFirst);
        when(phoneDao.get(phoneIdFirst)).thenReturn(Optional.of(testPhoneFirst));
        when(stockDao.get(phoneIdFirst)).thenReturn(Optional.of(stockFirst));
        when(testPhoneFirst.getPrice()).thenReturn(priceFirst);

        when(testPhoneSecond.getId()).thenReturn(phoneIdSecond);
        when(phoneDao.get(phoneIdSecond)).thenReturn(Optional.of(testPhoneSecond));
        when(stockDao.get(phoneIdSecond)).thenReturn(Optional.of(stockSecond));
        when(testPhoneSecond.getPrice()).thenReturn(priceSecond);

        cartService.addPhone(phoneIdFirst,quantityFirst);
        cartService.addPhone(phoneIdFirst,quantityFirst);
        cartService.addPhone(phoneIdSecond,quantityFirst);

        orderService.setDeliveryPrice(deliveryPrice);
    }

    @Test
    public void testCreateOrder() throws OutOfStockException {
        Order order = orderService.createOrder(cartService.getCart());
        assertEquals(cartSize.intValue(), order.getItems().size());
        assertEquals(cartService.getCart().getTotalCost(), order.getSubtotal());
        assertEquals(deliveryPrice.add(order.getSubtotal()), order.getTotalPrice());
    }

    @Test(expected = OutOfStockException.class)
    public void testOutOfStock() throws OutOfStockException {
        Order order = orderService.createOrder(cartService.getCart());
        order.getItems().get(0).setQuantity(stockFirst.getStock() + 1);
        orderService.placeOrder(order);
    }
}
