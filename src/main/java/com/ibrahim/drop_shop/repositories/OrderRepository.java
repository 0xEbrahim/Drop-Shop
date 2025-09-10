package com.ibrahim.drop_shop.repositories;

import com.ibrahim.drop_shop.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
