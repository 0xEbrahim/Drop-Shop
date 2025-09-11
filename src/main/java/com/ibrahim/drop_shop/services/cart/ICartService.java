package com.ibrahim.drop_shop.services.cart;

import com.ibrahim.drop_shop.models.Cart;
import com.ibrahim.drop_shop.services.cart.DTO.CartResponseDto;

import java.math.BigDecimal;
import java.util.List;

public interface ICartService {
     CartResponseDto getCartById(Long id);
     List<CartResponseDto> getAllCarts();
     BigDecimal getCartTotalPrice(Long id);
    void clearCart(Long id);
    Cart getCartByUserId(Long userId);
}
