package com.ibrahim.drop_shop.services.product;

import com.ibrahim.drop_shop.exceptions.NotFoundException;
import com.ibrahim.drop_shop.models.Category;
import com.ibrahim.drop_shop.models.Product;
import com.ibrahim.drop_shop.repositories.CategoryRepository;
import com.ibrahim.drop_shop.repositories.ProductRepository;
import com.ibrahim.drop_shop.services.product.DTO.AddProductDto;
import com.ibrahim.drop_shop.services.product.DTO.ProductResponseDto;
import com.ibrahim.drop_shop.services.product.DTO.UpdateProductDto;
import com.ibrahim.drop_shop.utils.ResponseTransformer;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ResponseTransformer responseTransformer;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ResponseTransformer responseTransformer){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.responseTransformer = responseTransformer;
    }


    @Override
    public ProductResponseDto addProduct(@NotNull AddProductDto productData) {
        Category category = categoryRepository
                .findById(productData.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category Not found"));
        Product product = productRepository.save(createProduct(productData, category));
        return responseTransformer.transformToDto(product, ProductResponseDto.class);
    }


    private Product createProduct(@NotNull AddProductDto productData, Category category) {
        Product product = Product.builder()
                .name(productData.getName())
                .brand(productData.getBrand())
                .category(category)
                .description(productData.getDescription())
                .price(productData.getPrice())
                .inventory(productData.getInventory())
                .build();
        product = productRepository.save(product);
        return product;
    }


    @Override
    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        return responseTransformer.transformToDto(product, ProductResponseDto.class);
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
    public ProductResponseDto updateProduct(UpdateProductDto productDto, Long id) {
        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        product = productRepository.save(updateExistingProduct(product, productDto));
        return responseTransformer.transformToDto(product, ProductResponseDto.class);
    }

    private Product updateExistingProduct(Product curProduct, UpdateProductDto productDto) {
        if(productDto.getName() != null){
            curProduct.setName(productDto.getName());
        }
        if(productDto.getPrice() != null){
            curProduct.setPrice(productDto.getPrice());
        }
        if(productDto.getDescription() != null) {
            curProduct.setDescription(productDto.getDescription());
        }
        if(productDto.getBrand() != null){
            curProduct.setBrand(productDto.getBrand());
        }
        if(productDto.getInventory() != null){
            curProduct.setInventory(productDto.getInventory());
        }
        Category category = categoryRepository
                .findById(productDto.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category Not found"));
        curProduct.setCategory(category);
        return curProduct;
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products
                .stream()
                .map(product -> responseTransformer.transformToDto(product, ProductResponseDto.class))
                .toList();
    }

    @Override
    public List<ProductResponseDto> getProductsByCategory(String category) {
        List<Product> products = productRepository.findByCategoryName(category);
        return products
                .stream()
                .map(product -> responseTransformer.transformToDto(product, ProductResponseDto.class))
                .toList();
    }

    @Override
    public List<ProductResponseDto> getProductsByBrand(String brandName) {
        List<Product> products = productRepository.findByBrand(brandName);
        return products
                .stream()
                .map(product -> responseTransformer.transformToDto(product, ProductResponseDto.class))
                .toList();
    }

    @Override
    public List<ProductResponseDto> getProductsByCategoryAndBrand(String category, String brand) {
        List<Product> products = productRepository.findByCategoryNameAndBrand(category,brand);
        return products
                .stream()
                .map(product -> responseTransformer.transformToDto(product, ProductResponseDto.class))
                .toList();
    }

    @Override
    public List<ProductResponseDto> getProductByName(String name) {
        List<Product> products = productRepository.findByName(name);
        return products
                .stream()
                .map(product -> responseTransformer.transformToDto(product, ProductResponseDto.class))
                .toList();
    }

    @Override
    public List<ProductResponseDto> getProductByBrandAndName(String brand, String name) {
        List<Product> products = productRepository.findByBrandAndName(brand, name);
        return products
                .stream()
                .map(product -> responseTransformer.transformToDto(product, ProductResponseDto.class))
                .toList();
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }
}
