package com.ibrahim.drop_shop.services.product;

import com.ibrahim.drop_shop.models.Product;

import java.util.List;

public interface IProductService {
    Product addProduct(Product product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(Product product, Long id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(Long categoryId);
    List<Product> getProductsByBrand(String brandName);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductByName(String name);
    List<Product> getProductByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);
}
