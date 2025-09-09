package com.ibrahim.drop_shop.repositories;

import com.ibrahim.drop_shop.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    void deleteAllByCartId(Long id);
}
