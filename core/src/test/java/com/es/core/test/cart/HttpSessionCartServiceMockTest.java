package com.es.core.test.cart;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.cart.HttpSessionCartService;
import com.es.core.model.phone.JdbcPhoneDao;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.stock.JdbcStockDao;
import com.es.core.model.phone.stock.OutOfStockException;
import com.es.core.model.phone.stock.Stock;

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
public class HttpSessionCartServiceMockTest {
    private Cart cart = new Cart();
    private JdbcPhoneDao phoneDao = mock(JdbcPhoneDao.class);
    private JdbcStockDao stockDao = mock(JdbcStockDao.class);
    private CartService cartService = new HttpSessionCartService(cart, phoneDao, stockDao);

    @Mock
    Phone testPhoneFirst;
    Long phoneIdFirst = 1003L;
    Integer quantityFirst = 11;
    Stock stockFirst = new Stock(testPhoneFirst, 20, 2);
    BigDecimal priceFirst = new BigDecimal(200);
        
    @Mock
    Phone testPhoneSecond;
    Long phoneIdSecond = 1006L;
    Integer quantitySecond = 11;
    Stock stockSecond = new Stock(testPhoneSecond, 20, 2);
    BigDecimal priceSecond = new BigDecimal(300);

    @Before
    public void setup() {
        when(phoneDao.get(phoneIdFirst)).thenReturn(Optional.of(testPhoneFirst));
        when(stockDao.get(phoneIdFirst)).thenReturn(Optional.of(stockFirst));
        when(testPhoneFirst.getPrice()).thenReturn(priceFirst);

        when(phoneDao.get(phoneIdSecond)).thenReturn(Optional.of(testPhoneSecond));
        when(stockDao.get(phoneIdSecond)).thenReturn(Optional.of(stockSecond));
        when(testPhoneSecond.getPrice()).thenReturn(priceSecond);
    }

    @Test
    public void testRightCalculateCart() throws OutOfStockException {
        cartService.addPhone(phoneIdFirst,quantityFirst);
        cartService.addPhone(phoneIdFirst,quantityFirst);
        cartService.addPhone(phoneIdSecond,quantityFirst);
        assertEquals(quantityFirst.intValue() * 3, cartService.getCart().getTotalQuantity().intValue());
        int expectedPrice = priceFirst.intValue() * quantityFirst * 2 + priceSecond.intValue() * quantityFirst;
        assertEquals(expectedPrice, cartService.getCart().getTotalCost().intValue());
    }
}
