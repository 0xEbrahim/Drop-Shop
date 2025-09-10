package com.ibrahim.drop_shop.services.order;

import com.ibrahim.drop_shop.services.order.DTO.OrderResponseDto;

import java.util.List;

public interface IOrderService {
    OrderResponseDto placeOrder(Long userId);
    OrderResponseDto getOrderById(Long id);
    List<OrderResponseDto> getAllOrders(Long userId);
}
