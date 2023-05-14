package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.model.order.Order;
import com.es.core.model.phone.stock.OutOfStockException;
import com.es.core.order.OrderService;
import com.es.phoneshop.web.dto.OrderDto;
import com.es.phoneshop.web.dto.converter.OrderDtoConverter;
import com.es.phoneshop.web.validation.OrderDtoValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/order")
public class OrderPageController {
    @Resource
    private OrderService orderService;

    @Resource
    private CartService cartService;

    @Resource
    private OrderDtoConverter orderDtoConverter;

    @Resource
    private OrderDtoValidator orderDtoValidator;

    private final String CART_ERROR_MESSAGE = "Some models are out of stock, cart was updated.";
    private final String CART_EMPTY_ERROR_MESSAGE = "Cart is empty, add something here.";

    @ModelAttribute("orderDto")
    public OrderDto setOrderDto() {
        Order order = orderService.createOrder(cartService.getCart());
        return orderDtoConverter.convertToDto(order);
    }

    @GetMapping
    public String getOrder(Model model) {
        Cart cart = cartService.getCart();

        if (cart.getTotalQuantity() < 1) {
            model.addAttribute("cart_error", CART_EMPTY_ERROR_MESSAGE);
            return "cart";
        }

        Order order = orderService.createOrder(cart);
        model.addAttribute("order", order);
        model.addAttribute("cart", cart);
        return "order";
    }

    @PostMapping
    public String placeOrder(@Valid @ModelAttribute("orderDto") OrderDto orderDto, BindingResult bindingResult,
                             Model model) {
        orderDtoValidator.validate(orderDto, bindingResult);
        if (bindingResult.hasErrors()) {
            Cart cart = cartService.getCart();
            Order order = orderService.createOrder(cart);
            model.addAttribute("order", order);
            model.addAttribute("cart", cart);
            return "order";
        }

        Order order = orderDtoConverter.convertToModel(orderDto);
        try {
            orderService.placeOrder(order);
        } catch (OutOfStockException e) {
            e.getErrorInfos().forEach(errorInfo -> cartService.remove(errorInfo.getPhoneId()));
            model.addAttribute("cart_error", CART_ERROR_MESSAGE);
            return "cart";
        }

        cartService.clearCart();
        return "redirect:/orderOverview/" + order.getSecureId();
    }
}
