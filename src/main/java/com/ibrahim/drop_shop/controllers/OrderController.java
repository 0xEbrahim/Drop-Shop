package com.ibrahim.drop_shop.controllers;


import com.ibrahim.drop_shop.services.order.DTO.OrderResponseDto;
import com.ibrahim.drop_shop.services.order.IOrderService;
import com.ibrahim.drop_shop.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> placeOrder(@RequestParam  Long userId) {
        OrderResponseDto order = orderService.placeOrder(userId);
        return ApiResponse.sendResponse("Order has been placed.", HttpStatus.CREATED, order);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getAllOrders(@RequestParam Long userId){
        List<OrderResponseDto> orders = orderService.getAllOrders(userId);
        return ApiResponse.sendResponse("Found", HttpStatus.OK, orders);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable("id") Long id) {
        OrderResponseDto order = orderService.getOrderById(id);
        return ApiResponse.sendResponse("Found", HttpStatus.OK, order);
    }

}
