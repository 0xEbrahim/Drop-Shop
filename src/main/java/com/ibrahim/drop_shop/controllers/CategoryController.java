package com.ibrahim.drop_shop.controllers;

import com.ibrahim.drop_shop.models.Category;
import com.ibrahim.drop_shop.services.category.DTO.CategoryResponseDto;
import com.ibrahim.drop_shop.utils.ApiResponse;
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
        return ApiResponse.sendResponse("Found",HttpStatus.OK, categoryService.getAllCategories());
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> createCategory(@RequestBody AddCategoryDto categoryDto) {
        CategoryResponseDto category = categoryService.addCategory(categoryDto);
        return ApiResponse.sendResponse("A new category has been created", HttpStatus.CREATED,  category);
    }

    @GetMapping("/name")
    public ResponseEntity<ApiResponse> getCategoryByName(@RequestParam("name") String name) {
        CategoryResponseDto category = categoryService.getCategoryByName(name);
        return ApiResponse.sendResponse("Found",HttpStatus.OK, category);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable("id") Long id) {
        CategoryResponseDto category = categoryService.getCategoryById(id);
        return ApiResponse.sendResponse("Found",HttpStatus.OK, category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable("id") Long id) {
        categoryService.deleteCategoryById(id);
        return ApiResponse.sendResponse("Category has been deleted",HttpStatus.OK, null);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCategoryById(@RequestBody UpdateCategoryDto dto,  @PathVariable("id") Long id) {
        CategoryResponseDto category = categoryService.updateCategory(dto, id);
        return ApiResponse.sendResponse("Category has been updated", HttpStatus.OK,category);
    }

}
