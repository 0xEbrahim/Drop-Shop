package com.ibrahim.drop_shop.repositories;

import com.ibrahim.drop_shop.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
