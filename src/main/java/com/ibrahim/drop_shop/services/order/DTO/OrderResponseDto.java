package com.ibrahim.drop_shop.services.order.DTO;

import com.ibrahim.drop_shop.DTOs.AttachedProductDto;
import com.ibrahim.drop_shop.DTOs.AttachedUserDto;
import com.ibrahim.drop_shop.enums.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class OrderResponseDto {
    private Long id;

    private LocalDateTime orderDate;

    private BigDecimal orderAmount;

    private OrderStatus orderStatus;

    private Set<OrdersItemViaOrder> orderItems;

    private AttachedUserDto user;
}

@Data
class OrdersItemViaOrder {
    private Long id;

    private BigDecimal price;

    private int quantity;

    private AttachedProductDto product;
}

