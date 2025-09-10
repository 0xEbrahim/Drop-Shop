package com.ibrahim.drop_shop.services.order;

import com.ibrahim.drop_shop.services.order.DTO.OrderResponseDto;

public interface IOrderService {
    OrderResponseDto placeOrder(Long id);
    OrderResponseDto getOrderById(Long id);
}
