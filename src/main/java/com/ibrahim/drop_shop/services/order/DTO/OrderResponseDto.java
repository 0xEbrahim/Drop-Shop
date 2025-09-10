package com.ibrahim.drop_shop.services.order.DTO;

import com.ibrahim.drop_shop.enums.OrderStatus;
import com.ibrahim.drop_shop.models.Product;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
}

@Data
class OrdersItemViaOrder {
    private Long id;

    private BigDecimal price;

    private int quantity;

    private ProductViaOrderItem product;
}

@Data
class ProductViaOrderItem {
    private Long id;
    private String name;
    private BigDecimal price;
}