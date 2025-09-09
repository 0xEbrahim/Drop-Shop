package com.ibrahim.drop_shop.services.cart.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class CartResponseDto {
    private Long id;

    private BigDecimal totalAmount;

    private Set<CartItemsViaCart> cartItems;
}

@Data
class CartItemsViaCart {
    private Long id;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private ProductViaCartItem product;
}

@Data
class ProductViaCartItem{
    private Long id;
    private String name;
}