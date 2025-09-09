package com.ibrahim.drop_shop.controllers;

import com.ibrahim.drop_shop.models.Category;
import com.ibrahim.drop_shop.response.ApiResponse;
import com.ibrahim.drop_shop.services.category.DTO.AddCategoryDto;
import com.ibrahim.drop_shop.services.category.DTO.UpdateCategoryDto;
import com.ibrahim.drop_shop.services.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    private final ICategoryService categoryService;

    @Autowired
    public CategoryController(ICategoryService categoryService){
        this.categoryService = categoryService;
    }


    @GetMapping()
    public ResponseEntity<ApiResponse> getAllCategories() {
        return ResponseEntity.status(201).body(new ApiResponse("Found", categoryService.getAllCategories()));
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> createCategory(@RequestBody AddCategoryDto categoryDto) {
        Category category = categoryService.addCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("A new category has been created", category));
    }

    @GetMapping("/name")
    public ResponseEntity<ApiResponse> getCategoryByName(@RequestParam("name") String name) {
        Category category = categoryService.getCategoryByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Found", category));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable("id") Long id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Found", category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable("id") Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Category has been deleted", null));

    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCategoryById(@RequestBody UpdateCategoryDto dto,  @PathVariable("id") Long id) {
        Category category = categoryService.updateCategory(dto, id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Category has been updated", category));
    }

}
