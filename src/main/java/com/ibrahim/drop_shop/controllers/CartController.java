package com.ibrahim.drop_shop.controllers;


import com.ibrahim.drop_shop.services.cart.DTO.CartResponseDto;
import com.ibrahim.drop_shop.services.cart.ICartService;
import com.ibrahim.drop_shop.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {
    private final ICartService cartService;

    public CartController(ICartService cartService){
        this.cartService = cartService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getAllCarts() {
        List<CartResponseDto> carts = cartService.getAllCarts();
        return ApiResponse.sendResponse("Found", HttpStatus.OK, carts);
    }

    @GetMapping("price/{id}")
    public ResponseEntity<ApiResponse> getCartTotalPrice(@PathVariable("id") Long id) {
        BigDecimal totalPrice = cartService.getCartTotalPrice(id);
        return ApiResponse.sendResponse("Success", HttpStatus.OK, totalPrice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable("id") Long id) {
        cartService.clearCart(id);
        return ApiResponse.sendResponse("Cart cleared successfully", HttpStatus.OK, null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable("id") Long id) {
        CartResponseDto cart = cartService.getCartById(id);
        return ApiResponse.sendResponse("Found", HttpStatus.OK, cart);
    }
}
