package com.ibrahim.drop_shop.services.cartItem;

import com.ibrahim.drop_shop.services.cartItem.DTO.AddItemToCartDto;

public interface ICartItemService {
    void addItemToCart(AddItemToCartDto dto);
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemQuantity(AddItemToCartDto dto);
}
