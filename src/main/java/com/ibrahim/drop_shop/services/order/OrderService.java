package com.ibrahim.drop_shop.services.order;
import com.ibrahim.drop_shop.enums.OrderStatus;
import com.ibrahim.drop_shop.exceptions.BadRequestException;
import com.ibrahim.drop_shop.exceptions.NotFoundException;
import com.ibrahim.drop_shop.models.Cart;
import com.ibrahim.drop_shop.models.Order;
import com.ibrahim.drop_shop.models.OrderItem;
import com.ibrahim.drop_shop.models.Product;
import com.ibrahim.drop_shop.repositories.OrderItemRepository;
import com.ibrahim.drop_shop.repositories.OrderRepository;
import com.ibrahim.drop_shop.repositories.ProductRepository;
import com.ibrahim.drop_shop.services.cart.ICartService;
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
    private final OrderItemRepository orderItemRepository;
    private final ResponseTransformer responseTransformer;
    private final ProductRepository productRepository;
    private final ICartService cartService;

    @Autowired
    public OrderService(OrderRepository orderRepository,OrderItemRepository orderItemRepository,  ProductRepository productRepository, ICartService cartService, ResponseTransformer responseTransformer) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartService = cartService;
        this.responseTransformer = responseTransformer;
    }

    @Override
    public OrderResponseDto placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> items = createOrderItem(order, cart);
        orderItemRepository.saveAll(items);
        BigDecimal total = calculateTotalAmount(items);
        order.setOrderAmount(total);
        orderRepository.save(order);
        cartService.clearCart(cart.getId());
        return responseTransformer.transformToDto(order, OrderResponseDto.class);
    }

    private Order createOrder(Cart cart) {
        return Order
                .builder()
                .user(cart.getUser())
                .orderStatus(OrderStatus.PENDING)
                .orderDate(LocalDateTime.now())
                .build();
    }

    private List<OrderItem> createOrderItem(Order order, Cart cart) {
        return cart.getCartItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            if(product.getInventory() - cartItem.getQuantity() < 0){
                throw new BadRequestException("insufficient product quantity");
            }
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

    @Override
    public List<OrderResponseDto> getAllOrders(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(o -> responseTransformer.transformToDto(o, OrderResponseDto.class)).toList();
    }
}
