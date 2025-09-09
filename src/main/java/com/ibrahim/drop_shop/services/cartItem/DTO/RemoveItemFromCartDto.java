package com.ibrahim.drop_shop.services.cartItem.DTO;

import lombok.Data;

@Data
public class RemoveItemFromCartDto {
    Long cartId;
    Long productId;
}
