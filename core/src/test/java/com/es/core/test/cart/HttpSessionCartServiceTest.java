package com.es.core.test.cart;

import com.es.core.cart.CartService;
import com.es.core.model.phone.stock.OutOfStockException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;

@ContextConfiguration("classpath:context/applicationContext-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class HttpSessionCartServiceTest {
    @Resource
    private CartService cartService;

    @Test
    public void testAddToCartSuccessfully() throws OutOfStockException {
        Long phoneIdFirst = 1003L;
        Long phoneIdSecond = 1006L;
        Integer phoneQuantity = 3;
        cartService.addPhone(phoneIdFirst,phoneQuantity);
        cartService.addPhone(phoneIdFirst,phoneQuantity);
        cartService.addPhone(phoneIdSecond,phoneQuantity);
        assertEquals(phoneQuantity.intValue() * 3, cartService.getCart().getTotalQuantity().intValue());
    }

    @Test(expected = OutOfStockException.class)
    public void testOutOfStockException() throws OutOfStockException {
        Long phoneIdFirst = 1003L;
        Integer phoneQuantity = 1000;
        cartService.addPhone(phoneIdFirst,phoneQuantity);
    }

}
