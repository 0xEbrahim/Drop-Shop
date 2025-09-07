package com.ibrahim.drop_shop.repositories;

import com.ibrahim.drop_shop.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
