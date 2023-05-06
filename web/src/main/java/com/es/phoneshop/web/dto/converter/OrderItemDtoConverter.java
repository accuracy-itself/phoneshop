package com.es.phoneshop.web.dto.converter;

import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.PhoneDao;
import com.es.phoneshop.web.dto.OrderItemDto;

import javax.annotation.Resource;

public class OrderItemDtoConverter implements DtoConverter<OrderItemDto, OrderItem> {
    @Resource
    private PhoneDao phoneDao;

    @Override
    public OrderItemDto convertToDto(OrderItem orderItem) {
        return new OrderItemDto(orderItem.getPhone().getId(),
                orderItem.getOrder().getId(),
                orderItem.getQuantity());
    }

    @Override
    public OrderItem convertToModel(OrderItemDto orderItemDto) {
        return new OrderItem(
                phoneDao.get(orderItemDto.getPhoneId()).get(),
                null,
                orderItemDto.getQuantity());
    }
}
