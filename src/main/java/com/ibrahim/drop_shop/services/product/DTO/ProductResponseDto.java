package com.ibrahim.drop_shop.services.product.DTO;

import com.ibrahim.drop_shop.services.category.DTO.CategoryResponseDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer inventory;
    private String brand;
    private CategoryViaProduct category;
    private List<ImageViaProduct> images;
}

@Data
class CategoryViaProduct {
    private Long id;
    private String name;
}

@Data
class ImageViaProduct {
    private Long id;
    private String downloadUrl;
}