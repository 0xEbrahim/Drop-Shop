package com.ibrahim.drop_shop.services.category.DTO;


import lombok.Data;

import java.util.List;

@Data
public class CategoryResponseDto {
    private Long id;
    private String name;
    private List<ProductsViaCategory> products;
}

@Data
class ProductsViaCategory {
    private Long id;
    private String name;
}
