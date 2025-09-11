package com.ibrahim.drop_shop.repositories;

import com.ibrahim.drop_shop.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
