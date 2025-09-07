package com.ibrahim.drop_shop.services.product;

import com.ibrahim.drop_shop.exceptions.NotFoundException;
import com.ibrahim.drop_shop.models.Category;
import com.ibrahim.drop_shop.models.Product;
import com.ibrahim.drop_shop.repositories.CategoryRepository;
import com.ibrahim.drop_shop.repositories.ProductRepository;
import com.ibrahim.drop_shop.services.product.DTO.AddProductDto;
import com.ibrahim.drop_shop.services.product.DTO.UpdateProductDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public Product addProduct(@NotNull AddProductDto productData) {
        Category category = categoryRepository
                .findById(productData.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category Not found"));
        return  productRepository.save(createProduct(productData, category));
    }

    private Product createProduct(@NotNull AddProductDto productData, Category category) {
        return Product.builder()
                .name(productData.getName())
                .brand(productData.getBrand())
                .category(category)
                .description(productData.getDescription())
                .price(productData.getPrice())
                .inventory(productData.getInventory())
                .build();
    }


    @Override
    public Product getProductById(Long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }

    @Override
    public void deleteProductById(Long id) {
         productRepository
                 .findById(id)
                    .ifPresentOrElse(productRepository::delete, () -> {
                        throw new NotFoundException("Product not found");
                    });
    }

    @Override
    public Product updateProduct(UpdateProductDto productDto, Long id) {
        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        return productRepository.save(updateExistingProduct(product, productDto));
    }

    private Product updateExistingProduct(Product curProduct, UpdateProductDto productDto) {
        curProduct.setBrand(productDto.getBrand());
        curProduct.setDescription(productDto.getDescription());
        curProduct.setName(productDto.getName());
        curProduct.setInventory(productDto.getInventory());
        curProduct.setPrice(productDto.getPrice());
        Category category = categoryRepository
                .findById(productDto.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category Not found"));
        curProduct.setCategory(category);
        return curProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brandName) {
        return productRepository.findByBrand(brandName);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }
}
