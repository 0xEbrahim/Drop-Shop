package com.ibrahim.drop_shop.services.product.DTO;

import com.ibrahim.drop_shop.services.category.DTO.CategoryResponseDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer inventory;
    private String brand;
    private CategoryViaProduct category;
}

@Data
class CategoryViaProduct {
    private Long id;
    private String name;
}