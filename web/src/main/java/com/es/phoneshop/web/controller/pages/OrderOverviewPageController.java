package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.model.order.Order;
import com.es.core.order.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Optional;

@Controller
@RequestMapping(value = "/orderOverview")
public class OrderOverviewPageController {
    @Resource
    private OrderService orderService;

    @Resource
    private CartService cartService;

    private final String ORDER_ERROR_MESSAGE = "Such order does not exist.";

    @GetMapping(value = "/{orderSecureId}")
    public String getOrder(@PathVariable String orderSecureId, Model model) {
        Cart cart = cartService.getCart();
        Optional<Order> order = orderService.getOrderBySecureId(orderSecureId);

        model.addAttribute("order", order.orElse(null));
        model.addAttribute("order_error", ORDER_ERROR_MESSAGE);
        model.addAttribute("cart", cart);
        return "orderOverview";
    }
}
