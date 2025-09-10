package com.ibrahim.drop_shop.services.category.DTO;


import com.ibrahim.drop_shop.DTOs.AttachedProductDto;
import lombok.Data;

import java.util.List;

@Data
public class CategoryResponseDto {
    private Long id;
    private String name;
    private List<AttachedProductDto> products;
}
