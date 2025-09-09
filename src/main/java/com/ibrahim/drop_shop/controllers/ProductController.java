package com.ibrahim.drop_shop.controllers;


import com.ibrahim.drop_shop.models.Product;
import com.ibrahim.drop_shop.response.ApiResponse;
import com.ibrahim.drop_shop.services.product.DTO.AddProductDto;
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
        Product product = productService.addProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Product has been created", product));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Found", products));
    }

    @GetMapping("/category")
    public ResponseEntity<ApiResponse> getProductByCategoryName(@RequestParam("name") String name) {
        List<Product> products = productService.getProductsByCategory(name);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Found", products));
    }

    @GetMapping("/brand")
    public ResponseEntity<ApiResponse> getProductByBrandName(@RequestParam("name") String name) {
        List<Product> products = productService.getProductsByBrand(name);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Found", products));
    }

    @GetMapping("name")
    public ResponseEntity<ApiResponse> getProductByName(@RequestParam("name") String name){
        List<Product> products = productService.getProductByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Found", products));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProductById(@PathVariable("id") Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Product has been deleted", null));
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable("id") Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Found", product));
    }

}
