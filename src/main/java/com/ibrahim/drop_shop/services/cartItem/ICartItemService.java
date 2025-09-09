package com.ibrahim.drop_shop.services.cartItem;

import com.ibrahim.drop_shop.services.cartItem.DTO.AddItemToCartDto;
import com.ibrahim.drop_shop.services.cartItem.DTO.RemoveItemFromCartDto;

public interface ICartItemService {
    void addItemToCart(AddItemToCartDto dto);
    void removeItemFromCart(RemoveItemFromCartDto dto);
    void updateItemQuantity(AddItemToCartDto dto);
}
