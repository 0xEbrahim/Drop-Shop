package com.ibrahim.drop_shop.services.category;

import com.ibrahim.drop_shop.models.Category;
import com.ibrahim.drop_shop.services.category.DTO.AddCategoryDto;
import com.ibrahim.drop_shop.services.category.DTO.CategoryResponseDto;
import com.ibrahim.drop_shop.services.category.DTO.UpdateCategoryDto;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    CategoryResponseDto addCategory(AddCategoryDto categoryDto);
    CategoryResponseDto getCategoryById(Long id);
    CategoryResponseDto getCategoryByName(String name);
    CategoryResponseDto updateCategory(UpdateCategoryDto categoryDto, Long id);
    void deleteCategoryById(Long id);
    List<CategoryResponseDto> getAllCategories();

}
