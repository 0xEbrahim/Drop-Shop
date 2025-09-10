package com.ibrahim.drop_shop.services.cart.DTO;

import com.ibrahim.drop_shop.DTOs.AttachedProductDto;
import com.ibrahim.drop_shop.DTOs.AttachedUserDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class CartResponseDto {
    private Long id;

    private BigDecimal totalAmount;

    private AttachedUserDto user;

    private Set<CartItemsViaCart> cartItems;
}

@Data
class CartItemsViaCart {
    private Long id;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private AttachedProductDto product;
}