package com.ibrahim.drop_shop.repositories;

import com.ibrahim.drop_shop.models.CartItem;
import com.ibrahim.drop_shop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    void deleteAllByCartId(Long id);

    CartItem findByProduct(Product product);

    void deleteCartItemsById(Long id);
}
