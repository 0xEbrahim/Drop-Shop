package com.ibrahim.drop_shop.services.order;

import com.ibrahim.drop_shop.enums.OrderStatus;
import com.ibrahim.drop_shop.exceptions.NotFoundException;
import com.ibrahim.drop_shop.models.Cart;
import com.ibrahim.drop_shop.models.Order;
import com.ibrahim.drop_shop.models.OrderItem;
import com.ibrahim.drop_shop.models.Product;
import com.ibrahim.drop_shop.repositories.OrderRepository;
import com.ibrahim.drop_shop.repositories.ProductRepository;
import com.ibrahim.drop_shop.services.order.DTO.OrderResponseDto;
import com.ibrahim.drop_shop.utils.ResponseTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService implements IOrderService{

    private final OrderRepository orderRepository;
    private final ResponseTransformer responseTransformer;
    private final ProductRepository productRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository,ProductRepository productRepository, ResponseTransformer responseTransformer) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.responseTransformer = responseTransformer;
    }

    @Override
    public OrderResponseDto placeOrder(Long userId) {
        return null;
    }

    private Order createOrder(Cart cart) {
        Order order = Order
                .builder()
                .orderStatus(OrderStatus.PENDING)
                .orderDate(LocalDateTime.now())
                .build();
        return order;
    }

    private List<OrderItem> createOrderItem(Order order, Cart cart) {
        return cart.getCartItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);
            return  OrderItem
                    .builder()
                    .order(order)
                    .price(cartItem.getUnitPrice())
                    .product(cartItem.getProduct())
                    .quantity(cartItem.getQuantity())
                    .build();

        }).toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> items) {
        return  items
                .stream()
                .map(item -> item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
}


    @Override
    public OrderResponseDto getOrderById(Long id) {
        Order order = orderRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found"));
        return responseTransformer.transformToDto(order, OrderResponseDto.class);
    }
}
