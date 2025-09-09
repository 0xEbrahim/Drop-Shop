package com.ibrahim.drop_shop.controllers;

import com.ibrahim.drop_shop.services.cartItem.CartItemService;
import com.ibrahim.drop_shop.services.cartItem.DTO.AddItemToCartDto;
import com.ibrahim.drop_shop.services.cartItem.DTO.RemoveItemFromCartDto;
import com.ibrahim.drop_shop.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService){
        this.cartItemService = cartItemService;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddItemToCartDto dto) {
        cartItemService.addItemToCart(dto);
        return ApiResponse.sendResponse("Success", HttpStatus.OK, null);
    }

    @DeleteMapping()
    public ResponseEntity<ApiResponse> removeFromCart(@RequestBody RemoveItemFromCartDto dto) {
        cartItemService.removeItemFromCart(dto);
        return ApiResponse.sendResponse("Success", HttpStatus.OK, null);
    }

    @PatchMapping()
    public ResponseEntity<ApiResponse> updateQuantity(@RequestBody AddItemToCartDto dto){
        cartItemService.updateItemQuantity(dto);
        return ApiResponse.sendResponse("Success", HttpStatus.OK, null);
    }

}
