package com.ibrahim.drop_shop.controllers;


import com.ibrahim.drop_shop.models.Product;
import com.ibrahim.drop_shop.response.ApiResponse;
import com.ibrahim.drop_shop.services.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final IProductService productService;

    @Autowired
    public ProductController(IProductService productService){
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Found", products));
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable("id") Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Found", product));
    }


}
