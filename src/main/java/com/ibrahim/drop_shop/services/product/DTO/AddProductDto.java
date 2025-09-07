package com.ibrahim.drop_shop.services.product.DTO;

import com.ibrahim.drop_shop.models.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddProductDto {
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Long categoryId;
}
