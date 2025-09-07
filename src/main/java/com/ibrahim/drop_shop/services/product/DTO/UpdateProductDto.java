package com.ibrahim.drop_shop.services.product.DTO;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class UpdateProductDto {
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Long categoryId;
}
