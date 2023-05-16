package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import com.es.core.order.OrderService;
import com.es.phoneshop.web.dto.MessageDto;
import com.es.phoneshop.web.dto.OrderStatusDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

@Controller
@RequestMapping(value = "/admin/orders")
public class OrdersPageController {
    @Resource
    OrderService orderService;

    private final String ORDER_NOT_EXIST_ERROR = "Such order does not exist";
    private final String ORDER_STATUS_WRONG_ERROR = "Such order status does not exist";
    private final String ORDER_STATUS_UPDATED = "Order status was updated successfully.";

    @GetMapping
    public String getOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "adminOrders";
    }

    @GetMapping(value = "/{orderId}")
    public String getOrder(@PathVariable("orderId") Long orderId, Model model) {
        Optional<Order> order = orderService.getOrderById(orderId);

        if (order.isPresent()) {
            model.addAttribute("order", order.get());
        } else {
            model.addAttribute("error_order", ORDER_NOT_EXIST_ERROR);
        }

        return "adminOrder";
    }

    @PostMapping(value = "/{orderId}")
    public ResponseEntity<String> changeOrderStatus(@PathVariable("orderId") Long orderId,
                                               @RequestBody OrderStatusDto orderStatusDto) throws JsonProcessingException {

        Optional<Order> order = orderService.getOrderById(orderId);
        ObjectMapper mapper = new ObjectMapper();
        if(order.isPresent()) {
            try {
                orderService.updateStatus(orderId, OrderStatus.valueOf(orderStatusDto.getStatus()));
                MessageDto messageDto = new MessageDto(ORDER_STATUS_UPDATED);
                return ResponseEntity.ok().body(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(messageDto));
            } catch (IllegalArgumentException e) {
                MessageDto messageDto = new MessageDto(ORDER_STATUS_WRONG_ERROR);
                return ResponseEntity.badRequest().body(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(messageDto));
            }
        } else {
            MessageDto messageDto = new MessageDto(ORDER_NOT_EXIST_ERROR);
            return ResponseEntity.badRequest().body(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(messageDto));
        }
    }
}
