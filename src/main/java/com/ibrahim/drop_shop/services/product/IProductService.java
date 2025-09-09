package com.ibrahim.drop_shop.services.product;

import com.ibrahim.drop_shop.models.Product;
import com.ibrahim.drop_shop.services.product.DTO.AddProductDto;
import com.ibrahim.drop_shop.services.product.DTO.ProductResponseDto;
import com.ibrahim.drop_shop.services.product.DTO.UpdateProductDto;

import java.util.List;

public interface IProductService {
    ProductResponseDto addProduct(AddProductDto product);
    ProductResponseDto getProductById(Long id);
    void deleteProductById(Long id);
    ProductResponseDto updateProduct(UpdateProductDto product, Long id);
    List<ProductResponseDto> getAllProducts();
    List<ProductResponseDto> getProductsByCategory(String category);
    List<ProductResponseDto> getProductsByBrand(String brandName);
    List<ProductResponseDto> getProductsByCategoryAndBrand(String category, String brand);
    List<ProductResponseDto> getProductByName(String name);
    List<ProductResponseDto> getProductByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);
}
