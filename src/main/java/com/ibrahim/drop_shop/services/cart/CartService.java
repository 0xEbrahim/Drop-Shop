package com.ibrahim.drop_shop.services.cart;

import com.ibrahim.drop_shop.exceptions.NotFoundException;
import com.ibrahim.drop_shop.models.Cart;
import com.ibrahim.drop_shop.models.CartItem;
import com.ibrahim.drop_shop.repositories.CartItemRepository;
import com.ibrahim.drop_shop.repositories.CartRepository;
import com.ibrahim.drop_shop.services.cart.DTO.CartResponseDto;
import com.ibrahim.drop_shop.services.cartItem.CartItemService;
import com.ibrahim.drop_shop.utils.ResponseTransformer;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


@Service
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ResponseTransformer responseTransformer;

    @Autowired
    public CartService(CartItemRepository cartItemRepository, CartRepository cartRepository, ResponseTransformer responseTransformer){
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.responseTransformer = responseTransformer;
    }

    @Override
    public CartResponseDto getCartById(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new NotFoundException("Cart not found."));
        return responseTransformer.transformToDto(cart, CartResponseDto.class);
    }

    @Override
    public List<CartResponseDto> getAllCarts() {
        List<Cart> carts = cartRepository.findAll();
        return carts
                .stream()
                .map(c -> responseTransformer.transformToDto(c, CartResponseDto.class))
                .toList();
    }

    @Override
    public BigDecimal getCartTotalPrice(Long id) {
        CartResponseDto cart = getCartById(id);
        return cart.getTotalAmount();
    }

    @Override
    @Transactional
    public void clearCart(Long id) {
        Cart cart = cartRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Cart not found."));
        cartItemRepository.deleteAllByCartId(id);
        cart.getCartItems().clear();
        cart.updateTotalAmount();
        cartRepository.save(cart);
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
}
