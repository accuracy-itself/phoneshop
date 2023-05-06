package com.es.phoneshop.web.dto.converter;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.phoneshop.web.dto.OrderDto;
import com.es.phoneshop.web.dto.OrderItemDto;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDtoConverter implements DtoConverter<OrderDto, Order> {
    @Resource
    private OrderItemDtoConverter orderItemDtoConverter;

    @Override
    public OrderDto convertToDto(Order order) {
        List<OrderItemDto> orderDtoItems = order.getItems().stream()
                .map(orderItem -> orderItemDtoConverter.convertToDto(orderItem))
                .collect(Collectors.toList());
        OrderDto orderDto = new OrderDto(order.getSubtotal(), order.getDeliveryPrice(), order.getTotalPrice());
        orderDto.setItems(orderDtoItems);

        return orderDto;
    }

    @Override
    public Order convertToModel(OrderDto orderDto) {
        Order order = new Order();
        order.setSubtotal(orderDto.getSubtotal());
        order.setDeliveryPrice(orderDto.getDeliveryPrice());
        List<OrderItem> orderItems = orderDto.getItems().stream()
                .map(orderItem -> orderItemDtoConverter.convertToModel(orderItem))
                .map(orderItem -> {
                    orderItem.setOrder(order);
                    return orderItem;
                })
                .collect(Collectors.toList());

        order.setItems(orderItems);
        order.setFirstName(orderDto.getFirstName());
        order.setLastName(orderDto.getLastName());
        order.setContactPhoneNo(orderDto.getContactPhoneNo());
        order.setDeliveryAddress(orderDto.getDeliveryAddress());
        order.setTotalPrice(order.getSubtotal().add(order.getDeliveryPrice()));
        return order;
    }
}
