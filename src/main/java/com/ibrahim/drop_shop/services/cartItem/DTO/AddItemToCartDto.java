package com.ibrahim.drop_shop.services.cartItem.DTO;

import lombok.Data;

@Data
public class AddItemToCartDto {
    private Long cartId;
    private Long productId;
    private int quantity;
}
