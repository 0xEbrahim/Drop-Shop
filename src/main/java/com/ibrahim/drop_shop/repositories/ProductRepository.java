package com.ibrahim.drop_shop.repositories;

import com.ibrahim.drop_shop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
