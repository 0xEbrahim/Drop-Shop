package com.ibrahim.drop_shop.services.category;

import com.ibrahim.drop_shop.models.Category;
import com.ibrahim.drop_shop.services.category.DTO.AddCategoryDto;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    Category addCategory(AddCategoryDto categoryDto);
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    Category updateCategory(AddCategoryDto categoryDto, Long id);
    void deleteCategoryById(Long id);
    List<Category> getAllCategories();

}
