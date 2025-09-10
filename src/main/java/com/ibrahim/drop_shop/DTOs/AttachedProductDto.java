package com.ibrahim.drop_shop.DTOs;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class AttachedProductDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private int quantity;
}
