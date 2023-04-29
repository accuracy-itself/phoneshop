package com.es.core.test.cart;

import com.es.core.cart.CartService;
import com.es.core.model.phone.stock.OutOfStockException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@ContextConfiguration("classpath:context/applicationContext-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class HttpSessionCartServiceTest {
    @Resource
    private CartService cartService;

    Long phoneIdFirst = 1003L;
    Long phoneIdSecond = 1006L;

    @Before
    public void init() {
        cartService.getCart().setItems(new ArrayList<>());
    }

    @Test
    public void testAddToCartSuccessfully() throws OutOfStockException {
        Integer phoneQuantity = 3;
        cartService.addPhone(phoneIdFirst,phoneQuantity);
        cartService.addPhone(phoneIdFirst,phoneQuantity);
        cartService.addPhone(phoneIdSecond,phoneQuantity);
        assertEquals(phoneQuantity.intValue() * 3, cartService.getCart().getTotalQuantity().intValue());
    }

    @Test(expected = OutOfStockException.class)
    public void testOutOfStockException() throws OutOfStockException {
        Integer phoneQuantity = 1000;
        cartService.addPhone(phoneIdFirst,phoneQuantity);
    }

    @Test
    public void testDeleteFromCartSuccessfully() throws OutOfStockException {
        Integer phoneQuantity = 3;
        cartService.addPhone(phoneIdFirst,phoneQuantity);
        cartService.remove(phoneIdFirst);
        cartService.addPhone(phoneIdSecond,phoneQuantity);
        assertEquals(phoneQuantity.intValue(), cartService.getCart().getTotalQuantity().intValue());
    }

    @Test
    public void testUpdateCartSuccessfully() throws OutOfStockException {
        Integer phoneQuantity = 3;
        Integer phoneQuantityNewFirst = 4;
        Integer phoneQuantityNewSecond = 1;
        int expectedQuantity = phoneQuantityNewFirst.intValue() + phoneQuantityNewSecond.intValue();
        Map<Long, Integer> cartUpdateValues = new HashMap<>();

        cartService.addPhone(phoneIdFirst,phoneQuantity);
        cartService.addPhone(phoneIdSecond,phoneQuantity);
        cartUpdateValues.put(phoneIdFirst, phoneQuantityNewFirst);
        cartUpdateValues.put(phoneIdSecond, phoneQuantityNewSecond);
        cartService.update(cartUpdateValues);
        
        assertEquals(expectedQuantity, cartService.getCart().getTotalQuantity().intValue());
    }
}
