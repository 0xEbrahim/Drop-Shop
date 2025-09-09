package com.ibrahim.drop_shop.controllers;


import com.ibrahim.drop_shop.services.cart.CartService;
import com.ibrahim.drop_shop.services.cart.DTO.CartResponseDto;
import com.ibrahim.drop_shop.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService){
        this.cartService = cartService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable("id") Long id) {
        CartResponseDto cart = cartService.getCartById(id);
        return ApiResponse.sendResponse("Found", HttpStatus.OK, cart);
    }
}
