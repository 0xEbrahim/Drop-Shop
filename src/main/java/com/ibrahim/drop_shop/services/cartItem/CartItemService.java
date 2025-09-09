package com.ibrahim.drop_shop.services.cartItem;

import com.ibrahim.drop_shop.exceptions.NotFoundException;
import com.ibrahim.drop_shop.models.Cart;
import com.ibrahim.drop_shop.models.CartItem;
import com.ibrahim.drop_shop.models.Product;
import com.ibrahim.drop_shop.repositories.CartItemRepository;
import com.ibrahim.drop_shop.repositories.CartRepository;
import com.ibrahim.drop_shop.repositories.ProductRepository;
import com.ibrahim.drop_shop.services.cartItem.DTO.AddItemToCartDto;
import com.ibrahim.drop_shop.services.cartItem.DTO.CartItemResponseDto;
import com.ibrahim.drop_shop.services.cartItem.DTO.RemoveItemFromCartDto;
import com.ibrahim.drop_shop.utils.ResponseTransformer;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CartItemService implements ICartItemService{
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ResponseTransformer responseTransformer;

    public CartItemService(
            CartItemRepository cartItemRepository,
            CartRepository cartRepository,
            ProductRepository productRepository,
            ResponseTransformer responseTransformer
    ){
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.responseTransformer = responseTransformer;
    }

    @Override
    @Transactional
    public void addItemToCart(AddItemToCartDto dto) {
        Cart cart = cartRepository
                .findById(dto.getCartId())
                .orElseThrow(() -> new NotFoundException("Cart not found"));
        Product product = productRepository
                .findById(dto.getProductId())
                .orElseThrow(() -> new NotFoundException("Product not found"));
        CartItem cartItem = cart
                .getCartItems()
                .stream()
                .filter(it -> it.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(createCartItem(cart, product, dto));
        if(cartItem.getId() != null){
            cartItem.setQuantity(cartItem.getQuantity() + dto.getQuantity());
        }
        cartItem.setTotalPrice();
        cartItemRepository.save(cartItem);
        cart.addItem(cartItem);
        cartRepository.save(cart);
    }

    private CartItem createCartItem(Cart cart, Product product, AddItemToCartDto dto) {
        return CartItem
                .builder()
                .cart(cart)
                .product(product)
                .quantity(dto.getQuantity())
                .unitPrice(product.getPrice())
                .build();
    }

    @Override
    @Transactional
    public void removeItemFromCart(RemoveItemFromCartDto dto) {
        Cart cart = cartRepository
                .findById(dto.getCartId())
                .orElseThrow(() -> new NotFoundException("Cart not found"));
        CartItem cartItem = cart
                .getCartItems()
                .stream()
                .filter(it -> it.getProduct().getId().equals(dto.getProductId()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Item not found"));
        cart.removeItem(cartItem);
        cartItemRepository.deleteCartItemsById(cartItem.getId());
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void updateItemQuantity(AddItemToCartDto dto) {
        Cart cart = cartRepository
                .findById(dto.getCartId())
                .orElseThrow(() -> new NotFoundException("Cart not found"));
        Product product = productRepository
                .findById(dto.getProductId())
                .orElseThrow(() -> new NotFoundException("Product not found"));
        CartItem cartItem = cart
                .getCartItems()
                .stream()
                .filter(it -> it.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Item not found"));
        cartItem.setQuantity(dto.getQuantity());
        cartItem.setTotalPrice();
        cartItemRepository.save(cartItem);
        cart.addItem(cartItem);
        cartRepository.save(cart);
    }
}
