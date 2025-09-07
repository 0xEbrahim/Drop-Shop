package com.ibrahim.drop_shop.repositories;

import com.ibrahim.drop_shop.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
