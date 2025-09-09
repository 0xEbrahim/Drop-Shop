package com.ibrahim.drop_shop.controllers;


import com.ibrahim.drop_shop.models.Product;
import com.ibrahim.drop_shop.utils.ApiResponse;
import com.ibrahim.drop_shop.services.product.DTO.AddProductDto;
import com.ibrahim.drop_shop.services.product.DTO.ProductResponseDto;
import com.ibrahim.drop_shop.services.product.DTO.UpdateProductDto;
import com.ibrahim.drop_shop.services.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final IProductService productService;

    @Autowired
    public ProductController(IProductService productService){
        this.productService = productService;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> createProduct(@RequestBody AddProductDto dto) {
        ProductResponseDto product = productService.addProduct(dto);
        return ApiResponse.sendResponse("Product has been created",HttpStatus.CREATED, product);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getProducts() {
        List<ProductResponseDto> products = productService.getAllProducts();
        return ApiResponse.sendResponse("Found", HttpStatus.OK, products);
    }

    @GetMapping("/category")
    public ResponseEntity<ApiResponse> getProductByCategoryName(@RequestParam("name") String name) {
        List<ProductResponseDto> products = productService.getProductsByCategory(name);
        return ApiResponse.sendResponse("Found", HttpStatus.OK, products);
    }

    @GetMapping("/brand")
    public ResponseEntity<ApiResponse> getProductByBrandName(@RequestParam("name") String name) {
        List<ProductResponseDto> products = productService.getProductsByBrand(name);
        return ApiResponse.sendResponse("Found", HttpStatus.OK, products);
    }

    @GetMapping("name")
    public ResponseEntity<ApiResponse> getProductByName(@RequestParam("name") String name){
        List<ProductResponseDto> products = productService.getProductByName(name);
        return ApiResponse.sendResponse("Found", HttpStatus.OK, products);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProductById(@PathVariable("id") Long id) {
        productService.deleteProductById(id);
        return ApiResponse.sendResponse("Product has been deleted", HttpStatus.OK, null);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable("id") Long id) {
        ProductResponseDto product = productService.getProductById(id);
        return ApiResponse.sendResponse("Found",HttpStatus.OK, product);
    }

    @PatchMapping("{id}")
    public ResponseEntity<ApiResponse> updateProductById(@RequestBody UpdateProductDto dto, @PathVariable("id") Long id) {
        ProductResponseDto product = productService.updateProduct(dto, id);
        return ApiResponse.sendResponse("Product has been updated", HttpStatus.OK, product);
    }
}
